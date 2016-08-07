class Car < Object
  let make: String
  let model: String

  let position: (Int, Int)?
  
  var isTesla = {
    self.make.lowercase is "tesla"
  }

  init(make, model)
    self.make = make.title_case
    self.model = model.title_case
    position = 0,0
  end
  
  func drive(x_delta?, y_delta?)
  	position + (x_delta, y_delta)
  end
end

# calls init
car = Car.new "chrysler" "sebring"
# ? at end requires boolean
car.isTesla?

car.drive(10, 0)



experimental
============
create car where make is "Chrysler" and model is "Sebring"
drive car 1 x_delta and 2 y_delta