package com.capgemini.stockmarket.dto.transactions;

import org.apache.commons.lang3.tuple.Pair;

public final class NumPair<L extends Number, R extends Number> extends Pair<L, R> {
	private static final long serialVersionUID = 1L;
	private final L left;
	private final R right;
	private final Double product;
	
	public static <L extends Number, R extends Number> NumPair<L, R> of(L left, R right) {
		return new NumPair<L, R>(left, right);
	}
	
	public NumPair(L left, R right) {
		super();
		this.left = left;
		this.right = right;
		this.product = left.doubleValue() * right.doubleValue();
	}
	
	public Double product() {
		return product;
	}

	@Override
	public R setValue(R value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public L getLeft() {
		return left;
	}

	@Override
	public R getRight() {
		return right;
	}
	
}
