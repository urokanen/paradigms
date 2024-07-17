init(N) :- calc(2, N).

calc(X, N) :-
		X < N,
		X2 is X * X,
		\+ calc_x(X2, X, N),
		NX is X + 1,
		calc(NX, N).

calc_x(X, S, N) :-
		X < N,
		prime(S),
		assert(composite(X)),
		assert(get_value(X, S)),
		NX is X + S, calc_x(NX, S, N).

prime(N) :- \+ composite(N).

check_divisors([]).
check_divisors([V]) :- prime(V).
check_divisors([V1, V2 | T]) :-
		V1 =< V2,
		prime(V1),
		check_divisors([V2 | T]).

prime_divisors(N, Divisors) :-
		number(N), !,
		calc_divisors(N, Divisors),
		check_divisors(Divisors).

get_n(N, N) :- prime(N).
get_n(N, V) :- get_value(N, V).

calc_divisors(1, []) :- !.
calc_divisors(N, [V | T]) :-
		get_n(N, V),
		N2 is N / V,
		calc_divisors(N2, T).

prime_divisors(N, Divisors) :-
		check_divisors(Divisors),
		calculate_n(N, Divisors).

calculate_n(1, []).
calculate_n(N, [V | T]) :-
		calculate_n(N1, T),
		N is N1 * V.

get_gcd(1, _, []) :- !.
get_gcd(1, [], _).
get_gcd(GCD, [V | T1], [V | T2]) :-
		get_gcd(G, T1, T2),
		GCD is G * V.

get_gcd(GCD, [V1 | T1], [V2 | T2]) :-
		V1 < V2,
		get_gcd(GCD, T1, [V2 | T2]).

get_gcd(GCD, [V1 | T1], [V2 | T2]) :-
		V2 < V1,
		get_gcd(GCD, [V1 | T1], T2).

gcd(A, B, GCD) :-
		prime_divisors(A, L1),
		prime_divisors(B, L2),
		get_gcd(GCD, L1, L2).

get_lcm(1, [], []) :- !.
get_lcm(LCM, [], [LCM]) :- !.
get_lcm(LCM, L, []) :- get_lcm(LCM, [], L), !.

get_lcm(LCM, [], [V | T]) :-
		get_lcm(LCM2, [], T), !,
		LCM is LCM2 * V.

get_lcm(LCM, [V | T1], [V | T2]) :-
		get_lcm(LCM2, T1, T2),
		LCM is LCM2 * V.

get_lcm(LCM, [V1 | T1], [V2 | T2]) :-
		V2 < V1,
		get_lcm(LCM, [V2 | T2], [V1 | T1]).

get_lcm(LCM, [V1 | T1], [V2 | T2]) :-
		V1 < V2,
		get_lcm(LCM2, T1, [V2 | T2]),
		LCM is LCM2 * V1.

lcm(A, B, LCM) :-
    prime_divisors(A, L1),
    prime_divisors(B, L2),
    get_lcm(LCM, L1, L2).
