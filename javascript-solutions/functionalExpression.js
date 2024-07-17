"use strict";

let operate = (op, ...args) => (...argv) => op(...args.map(el => el(...argv)));
let subtract = (a, b) => operate((a, b) => a - b, a, b);
let add = (a, b) => operate((a, b) => a + b, a, b);
let multiply = (a, b) => operate((a, b) => a * b, a, b);
let divide = (a, b) => operate((a, b) => a / b, a, b);
let negate = (a) => operate((a) => -a, a);
let avg5 = (a, b, c, d, e) => divide(sumAll(a, b, c, d, e), cnst(5));
let med3 = (a, b, c) => (...args) => [a, b, c].map(el => el(...args)).sort((a, b) => b - a)[1];
let sumAll = (...args) => (...argv) => args.map(el => el(...argv)).reduce((acc, curr) => acc + curr, 0);
let variable = (tag) => (...args) => (args[vars.get(tag)]);
let cnst = (val) => () => val;
let pi = cnst(Math.PI);
let e = cnst(Math.E);

const vars = new Map([["x", 0], ["y", 1], ["z", 2]]);
const special = new Set(["pi", "e"]);
const ops = new Map([["+", 2], ["-", 2], ["*", 2], ["/", 2],
    ["negate", 1], ["avg5", 5], ["med3", 3]]);
const parseOps = new Map([["+", add], ["-", subtract],
    ["*", multiply], ["/", divide], ["avg5", avg5], ["med3", med3], ["negate", negate]]);

let parse = function(expression) {
    let arr = expression.split(" ").filter(str => str.trim() !== "");
    const stack = [];
    for (let el of arr) {
        if (ops.has(el)) {
            stack.push(parseOps.get(el)(...stack.splice(-ops.get(el))));
        } else if (special.has(el)) {
            stack.push(el === "pi" ? pi : e);
        } else if (vars.has(el)) {
            stack.push(variable(el));
        } else {
            stack.push(cnst(parseInt(el)));
        }
    }
    return stack[0];
}