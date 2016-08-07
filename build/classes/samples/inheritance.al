class Alpha do
  def print_class do
    print("Alpha")
  end
end

class Beta < Alpha do
  def print_class do
    className = "Beta"
    while super != nil do
      prefix = " < "
      className.append(prefix.append(super.print_class()))
    end
    return className
  end
end

beta = Beta.create()
beta.print_class()
