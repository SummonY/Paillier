# Paillier
    Paillier公钥加密算法。
    Paillier公钥加密算法具有语义安全性，即给定明文m1,m2不存在多项式时间算法来区分E(m1)和E(m2)。

性质：
     Homomorphic
     Self-blinding
          

密钥生成：
     选择两大素数p和q，计算N=pq以及lambda=lcm(p-1,q-1)。随机选择g属于Z*,使得gcd(L(g^lambda mod N^2), N) = 1，其中L(x)=(x-1)/N。这里<N,g>为用户公钥，lambda为用户私钥。

加密过程：
     对于明文m，选择随机数r，密文c=E(m mod N, r mod N) = g^m * r^N mod N^2。
     此处，E(·)声明为使用公钥pk=<N, g>的加密操作。

解密过程：
     给定密文,其对应的明文D(c) = L(c^lambda mod N^2)/ L(g^lambda mod N^2) mod N。
     其中，D(·)声明为使用私钥sk=lambda的解密操作。
