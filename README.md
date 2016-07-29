What do I want?
===============

I want a language that Java programmers will feel home using, that doesn't have all the nasty "!= null" checks, that also has the ease-of-use that I love about Ruby.

**Note: This is a very early stage WIP**

What works
----------

 - function declaration
 - if statements
 - while loops
 - assignments
 
What doesn't work that you would otherwise expect to
----------------------------------------------------

 - no newline at the EOF
 - semi-colons as a terminator
 - pretty much everything else
 
Sample Aluminum Code:
=====================

Source
------

```
def print_hello() do
	print("hello")
end

i = 0
while(i < 10) do
	print_hello
	i = i + 1
end


print("done")
```

Output
------

```
hello
hello
hello
hello
hello
hello
hello
hello
hello
hello
done
```

Philosophies/Features
============

- Static typing
- Inferred typing where possible
- Optimize for programmer happiness (I want keywords like unless and ?)
- Pattern matching
- Functions are first-class objects
- Small boilerplate, fast to create new applications
- Incorporate Contract Programming and unit testing methodology
- Be able to build lightweight, standalone programs
- Nested Functions
- Functions literals
- Automatic memory management
- Unit tests as a first priority
- Exceptions
- Protected by default (like Java, not Swift)
- English words as keywords: and, or, etc

Inspiration
-----------

- Scala's maybe
- Ruby's unless
- Java's generics
- Swift's type system
- I like the ! in ruby functions to say "make this work or error" and have it be more lenient otherwise
- I like the _ in swift for I don't care about this variable
