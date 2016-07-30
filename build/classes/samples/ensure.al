print("starting ensures")
ensure(five() == 5)
// Assignment
a = 1
b = 2
c = 3
d = - 4
e = 5
f = true
g = false

// Test equality
ensure(a == 1)
ensure(a < 2)
ensure(b > a)
ensure(b >= a)
ensure(1 >= a)

ensure(-1 <= a)
ensure(a != b)

// Operations
ensure(((a ^ 4)) == 1)
ensure(b ^ c == 8)
ensure(4 * 4 == 16)
ensure(0 * 54732548 == 0)
ensure(2 * 2 * 2 == 8)

ensure(2*2*2 == 2 ^ 3)
ensure(10 / 2 == 5)
ensure(10 % 3 == 1)
ensure(1 + 1 == 2)
ensure(-1 - -1 == 0)

ensure(-1 - 1 == -2)
ensure(true && true)
ensure(true || false)
ensure(!(true && false))
ensure(!(false || false))

// Ternary
eIsFive = e == 5 ? true : false
dIsFive = d == 5 ? true : false
ensure(eIsFive)
ensure(!dIsFive) 

// Test functions
def five() do
	return 5
end

def true_func() do
	return true
end

def inNOut(in) do
	out = in
	return out
end

//five = five()
//ensure(five > 4 && five < 6)
ensure(five() == 5)



print("done")
