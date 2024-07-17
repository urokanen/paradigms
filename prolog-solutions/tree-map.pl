new_node(Key, Value, node(Key, Value, null, null, 1, 1, 0)). % node(Key, Value, Left, Right, Size, Height, Diff)

get_key(node(Key, _, _, _, _, _, _), Key).
get_left(node(_, _, Left, _, _, _, _), Left).
get_left(null, null).
get_right(node(_, _, _, Right, _, _, _), Right).
get_right(null, null).
get_size(node(_, _, _, _, Size, _, _), Size).
get_size(null, 0).
get_height(node(_, _, _, _, _, Height, _), Height).
get_height(null, 0).
get_diff(node(_, _, _, _, _, _, Diff), Diff).
get_diff(null, 0).

get_max(A, B, R) :- A > B, R is A.
get_max(A, B, R) :- A =< B, R is B.

set_key(node(_, Value, Left, Right, Size, Height, Diff), NewKey, node(NewKey, Value, Left, Right, Size, Height, Diff)).
set_left(node(Key, Value, _, Right, Size, Height, Diff), NewLeft, node(Key, Value, NewLeft, Right, Size, Height, Diff)).
set_right(node(Key, Value, Left, _, Size, Height, Diff), NewRight, node(Key, Value, Left, NewRight, Size, Height, Diff)).
set_value(node(Key, _, Left, Right, Size, Height, Diff), NewValue, node(Key, NewValue, Left, Right, Size, Height, Diff)).

recalc(node(Key, Value, Left, Right, _, _, _), node(Key, Value, Left, Right, Size, Height, Diff)) :-
	get_size(Left, LeftSize),
	get_size(Right, RightSize),
	TempSize is LeftSize + RightSize,
	Size is TempSize + 1,
	get_height(Left, LeftHeight),
	get_height(Right, RightHeight),
	get_max(LeftHeight, RightHeight, TempHeight),
	Height is TempHeight + 1,
	Diff is LeftHeight - RightHeight.

rotate_left(TreeMap, Result) :-
	 get_right(TreeMap, Right),
	 get_left(Right, Left),
	 set_right(TreeMap, Left, Temp),
	 recalc(Temp, NewTreeMap),
	 set_left(Right, NewTreeMap, TempAns),
	 recalc(TempAns, Result).

rotate_right(TreeMap, Result) :-
	get_left(TreeMap, Left),
	get_right(Left, Right),
	set_left(TreeMap, Right, Temp),
	recalc(Temp, NewTreeMap),
	set_right(Left, NewTreeMap, TempAns),
	recalc(TempAns, Result).

big_rotate_left(TreeMap, Result) :-
	get_right(TreeMap, Right),
	rotate_right(Right, Temp),
	set_right(TreeMap, Temp, NewTreeMap),
    rotate_left(NewTreeMap, Result).

big_rotate_right(TreeMap, Result) :-
	get_left(TreeMap, Left),
	rotate_left(Left, Temp),
	set_left(TreeMap, Temp, NewTreeMap),
	rotate_right(NewTreeMap, Result).

balance(TreeMap, Result) :-
	get_diff(TreeMap, Diff),
	Diff == -2,
	get_right(TreeMap, Right),
	get_diff(Right, RightDiff),
	(RightDiff == -1 ; RightDiff == 0), !,
	rotate_left(TreeMap, Result).

balance(TreeMap, Result) :-
	get_diff(TreeMap, Diff),
	Diff == 2,
	get_left(TreeMap, Left),
	get_diff(Left, LeftDiff),
	(LeftDiff == 1 ; LeftDiff == 0), !,
	rotate_right(TreeMap, Result).

balance(TreeMap, Result) :-
	get_diff(TreeMap, Diff),
	Diff == -2,
	get_right(TreeMap, Right),
	get_diff(Right, RightDiff),
	RightDiff == 1,
	get_left(Right, Left),
	get_diff(Left, LeftDiff),
	(LeftDiff == 1 ; LeftDiff == 0; LeftDiff == -1), !,
	big_rotate_left(TreeMap, Result).

balance(TreeMap, Result) :-
	get_diff(TreeMap, Diff),
	Diff == 2,
	get_left(TreeMap, Left),
	get_diff(Left, LeftDiff),
	LeftDiff == -1,
	get_right(Left, Right),
	get_diff(Right, RightDiff),
	(RightDiff == 1 ; RightDiff == 0; RightDiff == -1), !,
	big_rotate_right(TreeMap, Result).

balance(Result, Result).

map_put(null, Key, Value, Result) :-
    new_node(Key, Value, Result), !.

map_put(TreeMap, Key, Value, Result) :-
	get_key(TreeMap, Top),
	Top < Key, !,
	get_right(TreeMap, Right),
	map_put(Right, Key, Value, NewRight),
	set_right(TreeMap, NewRight, TempTreeMap),
	recalc(TempTreeMap, NewTreeMap),
	balance(NewTreeMap, Result).

map_put(TreeMap, Key, Value, Result) :-
	get_key(TreeMap, Top),
	Top > Key, !,
	get_left(TreeMap, Left),
	map_put(Left, Key, Value, NewLeft),
	set_left(TreeMap, NewLeft, TempTreeMap),
	recalc(TempTreeMap, NewTreeMap),
	balance(NewTreeMap, Result).

map_put(TreeMap, Key, Value, Result) :-
	get_key(TreeMap, Top),
	Top = Key, !,
	set_value(TreeMap, Value, Result).

map_get(node(Key, Value, _, _, _, _, _), Key, Value).

map_get(TreeMap, Key, Value) :-
	get_key(TreeMap, Top),
	Top > Key, !,
	get_left(TreeMap, Left),
	map_get(Left, Key, Value).

map_get(TreeMap, Key, Value) :-
	get_key(TreeMap, Top),
	Top < Key, !,
	get_right(TreeMap, Right),
	map_get(Right, Key, Value).

build([], Result, Result) :- !.
build([(Key, Value)| Tail], TreeMap, Result) :-
	map_put(TreeMap, Key, Value, NewTreeMap),
	build(Tail, NewTreeMap, Result).

map_build(ListMap, TreeMap) :-
	build(ListMap, null, TreeMap).

map_remove(null, _, null) :- !.

map_remove(TreeMap, Key, Result) :-
	get_key(TreeMap, Top),
	Top > Key, !,
	get_left(TreeMap, Left),
	map_remove(Left, Key, NewLeft),
	set_left(TreeMap, NewLeft, TempTreeMap),
	recalc(TempTreeMap, NewTreeMap),
	balance(NewTreeMap, Result).

map_remove(TreeMap, Key, Result) :-
	get_key(TreeMap, Top),
	Top < Key, !,
	get_right(TreeMap, Right),
	map_remove(Right, Key, NewRight),
	set_right(TreeMap, NewRight, TempTreeMap),
	recalc(TempTreeMap, NewTreeMap),
	balance(NewTreeMap, Result).

map_remove(node(Key, _, Left, null, _, _, _), Key, Left) :- !.

get_next(node(Key, _, null, _, _, _, _), Key) :- !.

get_next(TreeMap, Key) :-
	get_left(TreeMap, Left),
	get_next(Left, Key).

map_remove(TreeMap, Key, Result) :-
	get_right(TreeMap, Right),
	get_next(Right, Next),
	map_get(Right, Next, Value),
	map_remove(Right, Next, NewRight),
	set_key(TreeMap, Next, Temp),
	set_value(Temp, Value, TempTreeMap),
	set_right(TempTreeMap, NewRight, NewTreeMap),
	recalc(NewTreeMap, Ans),
	balance(Ans, Result).

map_headMapSize(null, _, 0).

map_headMapSize(TreeMap, ToKey, Size) :-
	get_key(TreeMap, Key),
	Key < ToKey, !,
	get_right(TreeMap, Right),
	map_headMapSize(Right, ToKey, Ans),
	get_left(TreeMap, Left),
	get_size(Left, LeftSize),
	TempSize is Ans + LeftSize,
	Size is TempSize + 1.

map_headMapSize(TreeMap, ToKey, Size) :-
	get_key(TreeMap, Key),
	Key = ToKey, !,
	get_right(TreeMap, Right),
	map_headMapSize(Right, ToKey, Ans),
	get_left(TreeMap, Left),
	get_size(Left, LeftSize),
	Size is Ans + LeftSize.

map_headMapSize(TreeMap, ToKey, Size) :-
	get_key(TreeMap, Key),
	Key > ToKey, !,
	get_left(TreeMap, Left),
	map_headMapSize(Left, ToKey, Size).

map_tailMapSize(TreeMap, FromKey, Size) :-
	get_size(TreeMap, TreeSize),
	map_headMapSize(TreeMap, FromKey, Temp),
	Size is TreeSize - Temp.

get_less(node(Key, _, _, null, _, _, _), Key) :- !.

get_less(TreeMap, Key) :-
    get_right(TreeMap, Right),
    get_less(Right, Key).

map_ceilingEntry(node(Key, Value, _, _, _, _, _), Key, (Key, Value)) :- !.

map_ceilingEntry(TreeMap, Key, Entry) :-
    get_key(TreeMap, Top),
    Top < Key, !,
    get_right(TreeMap, Right),
    map_ceilingEntry(Right, Key, Entry).

map_ceilingEntry(node(Key, Value, null, _, _, _, _), _, (Key, Value)) :- !.

map_ceilingEntry(TreeMap, Key, Entry) :-
    get_key(TreeMap, Top),
    Top > Key,
    get_left(TreeMap, Left),
    get_less(Left, LeftKey),
    Key =< LeftKey, !,
    map_ceilingEntry(Left, Key, Entry).

map_ceilingEntry(node(Key, Value, _, _, _, _, _), _, (Key, Value)).

map_removeCeiling(TreeMap, Key, Result) :-
    map_ceilingEntry(TreeMap, Key, (NextKey, NextValue)), !,
    map_remove(TreeMap, NextKey, Result).

map_removeCeiling(TreeMap, _, TreeMap).