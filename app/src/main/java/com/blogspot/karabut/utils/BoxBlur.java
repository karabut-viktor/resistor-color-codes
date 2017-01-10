package com.blogspot.karabut.utils;

import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.util.Log;

public class BoxBlur {
	private static final int RED_OFFSET   = 0;
	private static final int GREEN_OFFSET = 1;
	private static final int BLUE_OFFSET  = 2;
	private static final int ALPHA_OFFSET = 3;
	private static final int BYTES_PER_PIXEL = 4;
	
	public static void append(Bitmap bitmap, int radius) {
		if (Bitmap.Config.ARGB_8888 != bitmap.getConfig()) {
			Log.e("BoxBlur", "Expected ARGB_8888 bitmap, but got " + bitmap.getConfig() + ". Aborting.");
			return;
		}
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		byte[] buf1 = new byte[w*h*BYTES_PER_PIXEL];
		byte[] buf2 = new byte[w*h*BYTES_PER_PIXEL];
		
		bitmap.copyPixelsToBuffer(ByteBuffer.wrap(buf1));
		
		blurHorizontal(buf1, buf2, w, h, radius);
		blurVertical(buf2, buf1, w, h, radius);
		
		bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(buf1));
	}
	
	private static void blurHorizontal(byte[] src, byte[] dest, int w, int h, int radius) {
		int size = radius*2 + 1;
		int maxj = w - size;
	
		for (int i = 0; i < h; i++) {
			for(int j = 0; j <= maxj; j++) {
				int index = i*w*BYTES_PER_PIXEL + j*BYTES_PER_PIXEL;
				int sumR = 0, sumG = 0, sumB = 0, sumA = 0;
				for (int offset = 0; offset < size; offset++) {
					int indexOffset = index + offset*BYTES_PER_PIXEL;
					sumR += src[indexOffset + RED_OFFSET  ] & (0xff);
					sumG += src[indexOffset + GREEN_OFFSET] & (0xff);
					sumB += src[indexOffset + BLUE_OFFSET ] & (0xff);
					sumA += src[indexOffset + ALPHA_OFFSET] & (0xff);
				}
				int indexCenter = index + radius*BYTES_PER_PIXEL;
				dest[indexCenter + RED_OFFSET  ] = normalize(sumR, size, 0); 
				dest[indexCenter + GREEN_OFFSET] = normalize(sumG, size, 0); 
				dest[indexCenter + BLUE_OFFSET ] = normalize(sumB, size, 0); 
				dest[indexCenter + ALPHA_OFFSET] = normalize(sumA, size, 0); 
			}
		}
	}
	
	private static void blurVertical(byte[] src, byte[] dest, int w, int h, int radius) {
		int size = radius*2 + 1;
		int maxi = h - size;
		
		for (int i = 0; i <= maxi; i++) {
			for(int j = 0; j < w; j++) {
				int index = i*w*BYTES_PER_PIXEL + j*BYTES_PER_PIXEL;
				int sumR = 0, sumG = 0, sumB = 0, sumA = 0;
				for (int offset = 0; offset < size; offset++) {
					int indexOffset = index + offset*w*BYTES_PER_PIXEL;
					sumR += src[indexOffset + RED_OFFSET  ] & (0xff);
					sumG += src[indexOffset + GREEN_OFFSET] & (0xff);
					sumB += src[indexOffset + BLUE_OFFSET ] & (0xff);
					sumA += src[indexOffset + ALPHA_OFFSET] & (0xff);
				}
				int indexCenter = index + radius*w*BYTES_PER_PIXEL;
				dest[indexCenter + RED_OFFSET  ] = normalize(sumR, size, 0); 
				dest[indexCenter + GREEN_OFFSET] = normalize(sumG, size, 0); 
				dest[indexCenter + BLUE_OFFSET ] = normalize(sumB, size, 0); 
				dest[indexCenter + ALPHA_OFFSET] = normalize(sumA, size, 0); 
			}
		}
	}
	
	private static byte normalize(int v, int f, int o) {
		v = v/f + o;
		if (v < 0) {
			return (byte)0x00;
		} else if (v > 255) {
			return (byte)0xff;
		} else {
			return (byte)v;
		}
	}
}
