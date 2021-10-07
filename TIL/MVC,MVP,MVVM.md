## MVC
- Model
	- stores application data
	- has no knowledge about the interface
	- domain logic, communicates with db and network layers
- View
	- UI
	- visulization of the data
- Controller
	- establishes relationship between View and the Model
	- application logic
	

## MVP
- Model
	- stores data
	- domain logic
- View
	- UI
- Presenter
	- fetches the data from the Model and applies UI logic
	- *manages the state of the View*

<img src = "https://media.geeksforgeeks.org/wp-content/uploads/20201024233154/MVPSchema.png"/>
	
## MVVM
separates data presentation logic from the business logic</br>

- Model
	- abstraction of the data sources
	- works together with ViewModel to get and save data
- View
	- infroms the ViewModel about the user's action
	- *observes ViewModel*
	- does not contain application logic
- ViewModel
	- exposes *data streams*
	- link between the Model and the View

<img src = "https://media.geeksforgeeks.org/wp-content/uploads/20201002215007/MVVMSchema.png"/>

### traits of MVVM architecture
- industry-recognized architecture pattern for applications
- event-driven as it uses data binding
- mutiple View mapped with a single ViewModel
- the View takes the input from the user and acts as the entry point
- higher unit testability


[reference](https://www.geeksforgeeks.org/difference-between-mvc-mvp-and-mvvm-architecture-pattern-in-android/)