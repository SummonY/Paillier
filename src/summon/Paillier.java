package summon;

import java.math.BigInteger;
import java.util.Random;

public class Paillier {
	private static BigInteger p;
	private static BigInteger q;
	private static BigInteger N;
	private static BigInteger N2;
	private static BigInteger lambda;
	private static BigInteger g;
	
	// 最小公倍数
	private static BigInteger LCM(BigInteger p, BigInteger q) {
		BigInteger gcd, mul;
		mul = p.multiply(q);
		gcd = p.gcd(q);
		return mul.divide(gcd);
	}
	
	// 生成密钥
	public static void kenGen() {
		Random rnd = new Random();
		p = BigInteger.probablePrime(512, rnd);
		rnd = new Random();
		q = BigInteger.probablePrime(512, rnd);
		N = p.multiply(q);
		N2 = N.multiply(N);
		
		System.out.println("N = " + N + "\np = " + p + "\nq = " + q);
		
		// 密钥
		lambda = LCM(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
		
		rnd = new Random();
		BigInteger L;
		do {
			g = new BigInteger(160, rnd).mod(N2);
			L = g.modPow(lambda, N2).subtract(BigInteger.ONE).divide(N);
		} while (!L.gcd(N).equals(BigInteger.ONE));
		
		System.out.println("lambda = " + lambda + "\ng = " + g);
	}
	
	// 加密
	public static BigInteger encrypt(BigInteger m) {
		BigInteger c, r, gm, rn;
		Random rnd = new Random();
		r = new BigInteger(512, rnd).mod(N);
		gm = g.modPow(m, N2);
		rn = r.modPow(N, N2);
		c = gm.multiply(rn).mod(N2);
		return c;
	}
	
	// 解密
	public static BigInteger decrypt(BigInteger c) {
		BigInteger fz =  c.modPow(lambda, N2);
		fz = fz.subtract(BigInteger.ONE);
		fz = fz.divide(N);
		BigInteger fm = g.modPow(lambda, N2);
		fm = fm.subtract(BigInteger.ONE);
		fm = fm.divide(N);
		return fz.multiply(fm.modInverse(N)).mod(N);
	}
	
	public static void main(String[] args) {
		BigInteger m, c;
		
		kenGen();
		Random rnd = new Random();
		m = new BigInteger(1024, rnd).mod(N);
		System.out.println("初始化明文：" + m);
		c = encrypt(m);
		System.out.println("加密后密文：" + c);
		System.out.println("解密后明文：" + decrypt(c));
	}
}
