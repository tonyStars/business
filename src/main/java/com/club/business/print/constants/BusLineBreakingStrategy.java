package com.club.business.print.constants;

import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.breaker.BreakPointsProvider;
import org.xhtmlrenderer.layout.breaker.LineBreakingStrategy;

/**
 * 必要参数类
 *
 * @author Tom
 * @date 2019-12-16
 */
public class BusLineBreakingStrategy implements LineBreakingStrategy {

	@Override
	public BreakPointsProvider getBreakPointsProvider(String text, String lang, CalculatedStyle style) {
		return new BusBreakAnywhereLineBreakStrategy(text);
	}
}
