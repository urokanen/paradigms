"use strict";

const createConst = function () {
    const result = function (val) {this.val = val;};
    result.prototype.evaluate = function () {return this.val;};
    result.prototype.toString = function () {return this.val.toString();};
    result.prototype.diff = function (_) {return cnst0;};
    result.prototype.prefix = function () {return this.val.toString();};
    result.prototype.postfix = function () {return this.val.toString();};
    return result;
}

const createVariable = function () {
    const result = function (tag) {this.tag = tag;};
    result.prototype.evaluate = function (...argv) {return argv[vars.get(this.tag)];};
    result.prototype.toString = function () {return this.tag.toString();};
    result.prototype.diff = function (tag) {return tag === this.tag ? cnst1 : cnst0;};
    result.prototype.prefix = function () {return this.tag.toString();};
    result.prototype.postfix = function () {return this.tag.toString();};
    return result;
}

const operationsPrototype = {
    toString: function () {return this.args.join(" ") + " " + this.operation;},
    evaluate: function (...argv) {return this.calculate(...this.args.map(el => el.evaluate(...argv)));},
    prefix: function () {return "(" + this.operation + " " + this.args.map(el => el.prefix()).join(" ") + ")";},
    postfix: function () {return "(" + this.args.map(el => el.postfix()).join(" ") + " " + this.operation + ")";},
    diff: function (tag) {return this.diffOps(this.args, this.args.map(el => el.diff(tag)), tag)}
}

const createOperation = function (calculate, operation, diffOps) {
    const result = function (...args) {this.args = args;};
    result.prototype = Object.create(operationsPrototype);
    result.prototype.calculate = calculate;
    result.prototype.operation = operation;
    result.prototype.diffOps = diffOps;
    return result;
}

const negateDiff = function ([a], [da]) {return new Negate(da);};
const addDiff = function ([a, b], [da, db]) {return new Add(da, db);};
const subtractDiff = function ([a, b], [da, db]) {return new Subtract(da, db);};
const multiplyDiff = function ([a, b], [da, db]) {return new Add(new Multiply(da, b), new Multiply(db, a));};
const divideDiff = function ([a, b], [da, db]) {
    return new Divide(new Subtract(new Multiply(da, b), new Multiply(db, a)), new Multiply(b, b));};
const hypotDiff = function (args, dArgs, tag) {
    return new Add(new Multiply(args[0], args[0]), new Multiply(args[1], args[1])).diff(tag);};
const hmeanDiff = function (args, dArgs, tag) {
    return new Divide(cnst2, new Add(new Divide(cnst1, args[0]), new Divide(cnst1, args[1]))).diff(tag);};
const arithMeanDiff = function (args, dArgs) {return new ArithMean(...dArgs);};
const geomMeanDiff = function (args, dArgs, tag) {
    return new Multiply(new Multiply(new Pow(multiplyArgs(...args),
        new Const(1 / args.length - 1)), new Const(1 / args.length)), multiplyArgs(...args).diff(tag));};
const harmMeanDiff = function (args, dArgs, tag) {
    return new Divide(new Negate(new Multiply(new Const(args.length), harmonicArgs(...args).diff(tag))),
        new Multiply(harmonicArgs(...args), harmonicArgs(...args)));};

const harmonicArgs = (...args) => args.reduce((acc, curr) => new Add(new Divide(cnst1, curr), acc), cnst0);
const multiplyArgs = (...args) => args.reduce((acc, curr) => new Multiply(acc, curr), cnst1);
const arithMean = (...args) => (args.map(el => el).reduce((acc, curr) => acc + curr, 0)) / args.length;
const geomMean = (...args) => Math.pow(Math.abs(args.reduce((acc, curr) => acc * curr, 1)), 1 / args.length);
const harmMean = (...args) => args.length / args.reduce((acc, curr) => acc + 1 / curr, 0);

const Hypot = createOperation((x, y) => (x * x + y * y), "hypot", hypotDiff);
const HMean = createOperation((x, y) => (2 / (1 / x + 1 / y)), "hmean", hmeanDiff);
const Const = createConst();
const Variable = createVariable();
const Negate = createOperation((a) => -a, "negate", negateDiff);
const Add = createOperation((a, b) => a + b, "+", addDiff);
const Subtract = createOperation((a, b) => a - b, "-", subtractDiff);
const Multiply = createOperation((a, b) => a * b, "*", multiplyDiff);
const Divide = createOperation((a, b) => a / b, "/", divideDiff);
const ArithMean = createOperation(arithMean, "arithMean", arithMeanDiff);
const GeomMean = createOperation(geomMean, "geomMean", geomMeanDiff);
const HarmMean = createOperation(harmMean, "harmMean", harmMeanDiff);
const Pow = createOperation((a, b) => Math.pow(a, b));

const cnst2 = new Const(2);
const cnst1 = new Const(1);
const cnst0 = new Const(0);

const vars = new Map([["x", 0], ["y", 1], ["z", 2]]);
const ops = new Map([["+", 2], ["-", 2], ["*", 2], ["/", 2],
    ["negate", 1], ["hypot", 2], ["hmean", 2]]);
const specialOps = new Set(["arithMean", "geomMean", "harmMean"]);

const parseOperations = function (op, ...args) {
    switch (op) {
        case "+": return new Add(...args);
        case "-": return new Subtract(...args);
        case "*": return new Multiply(...args);
        case "/": return new Divide(...args);
        case "hypot": return new Hypot(...args);
        case "hmean": return new HMean(...args);
        case "arithMean": return new ArithMean(...args);
        case "geomMean": return new GeomMean(...args);
        case "harmMean": return new HarmMean(...args);
        default: return new Negate(...args);
    }
}

const parse = function (expression) {
    const arr = expression.split(" ").filter(str => str.trim() !== "");
    const stack = [];
    for (let el of arr) {
        if (ops.has(el)) {
            stack.push(parseOperations(el, ...stack.splice(-ops.get(el))));
        } else {
            stack.push(vars.has(el) ? new Variable(el) : new Const(parseInt(el)));
        }
    }
    return stack[0];
}

function SpecialError(message) {
    this.message = message;
}

function BracketsError(message) {
    SpecialError.call(this, message);
}

function IncorrectNumberOfArgumentsError(message) {
    SpecialError.call(this, message);
}

function EmptyOperationError(message) {
    SpecialError.call(this, message);
}

function IncorrectArgumentError(message) {
    SpecialError.call(this, message);
}

function IncorrectExpressionError(message) {
    SpecialError.call(this, message);
}

SpecialError.prototype = Object.create(Error.prototype);
BracketsError.prototype = Object.create(SpecialError.prototype);
EmptyOperationError.prototype = Object.create(SpecialError.prototype);
IncorrectArgumentError.prototype = Object.create(SpecialError.prototype);
IncorrectExpressionError.prototype = Object.create(SpecialError.prototype);
IncorrectNumberOfArgumentsError.prototype = Object.create(SpecialError.prototype);

const parseExpression = function () {
    let ind = 0;
    let open = 0;
    const stack = [];
    const countArgs = [];
    const opPosition = [];
    while (ind < arr.length) {
        if (arr[ind] === "(") {
            open++;
            countArgs.push(0);
            opPosition.push(0);
        } else if (arr[ind] === ")") {
            if (open === 0) {
                throw new BracketsError("The parentheses in the expression are not correct!");
            } else {
                open--;
                const s = countArgs.pop();
                const opInd = opPosition.pop();
                if (opInd > 0) {
                    countArgs.push(countArgs.pop() + 1);
                    if (ops.has(arr[opInd])) {
                        if (ops.get(arr[opInd]) === s) {
                            stack.push(parseOperations(arr[opInd], ...stack.splice(-ops.get(arr[opInd]))));
                        } else {
                            throw new IncorrectNumberOfArgumentsError("Incorrect number of arguments for operation: "
                                + arr[opInd] + ", position: " + opInd + ", count of arguments: " + s);
                        }
                    } else {
                        stack.push(parseOperations(arr[opInd], ...stack.splice(-s)));
                    }
                } else {
                    throw new EmptyOperationError("Empty operation before position: " + ind);
                }
            }
        } else if (vars.has(arr[ind]) || !isNaN(Number(arr[ind]))) {
            countArgs.push(countArgs.pop() + 1);
            stack.push(vars.has(arr[ind]) ? new Variable(arr[ind]) : new Const(parseInt(arr[ind])));
        } else if (ops.has(arr[ind]) || specialOps.has(arr[ind])) {
            if (opPosition.pop() === 0) {
                opPosition.push(ind);
            } else {
                throw new IncorrectArgumentError("Incorrect argument at position: " + ind + ", argument: " + arr[ind]);
            }
        } else {
            throw new IncorrectArgumentError("Incorrect argument at position: " + ind + ", argument: " + arr[ind]);
        }
        ind++;
    }
    if (open === 0 && stack.length === 1) {
        return stack[0];
    }
    throw new IncorrectExpressionError("Incorrect expression found");
}

let arr;

const parsePrefix = function (expression) {
    return parsePostfix(expression);
}

const parsePostfix = function (expression) {
    expression = expression.replace(/\(/g, " ( ").replace(/\)/g, " ) ");
    arr = expression.split(" ").filter(token => token !== "");
    return parseExpression();
}