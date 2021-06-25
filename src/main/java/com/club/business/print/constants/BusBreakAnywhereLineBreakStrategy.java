package com.club.business.print.constants;

import org.xhtmlrenderer.layout.breaker.BreakPoint;
import org.xhtmlrenderer.layout.breaker.BreakPointsProvider;

/**
 * 必要参数类
 *
 * @author Tom
 * @date 2018年11月29日
 */
public class BusBreakAnywhereLineBreakStrategy implements BreakPointsProvider {

	private String currentString;
	int position = 0;

	public BusBreakAnywhereLineBreakStrategy(String currentString) {
		this.currentString = currentString;
	}

	@Override
	public BreakPoint next() {
		if (position + 1 > currentString.length()) return BreakPoint.getDonePoint();
		return new BreakPoint(++position);
	}
}
