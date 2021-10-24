package com.marcelo.gameserver.handlers;


import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayUtils {
	int getValueToAdd(int currentValue) {
		if (currentValue % 3 == 0) {
			return 0;
		} else if (currentValue % 3 == 1) {
			return -1;
		} else {
			return 1;
		}
	}
}
