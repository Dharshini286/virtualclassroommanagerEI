Virtual Classroom Manager
DESIGN PATTERN USED
Observer Pattern:Observer interface AssignmentObserver class implementing Observer Observable class
       *This pattern is used to allow an object to notify other objects about changes in its state.
       ObservableClassroom inherits from Classroom and includes an instance of Observable to notify observers when an assignment is submitted.*
Factory Method Pattern: ClassroomFactory and ObservableClassroomFactory classes
    *This pattern is used to create objects without specifying the exact class of object that will be created. 
    The ClassroomFactory and ObservableClassroomFactory classes provide methods to create instances of Classroom and ObservableClassroom, respectively.*
Singleton Pattern:VirtualClassroomManager class    
    *Description: This pattern ensures that a class has only one instance and provides a global point of access to it. 
    VirtualClassroomManager uses a private constructor and a static getInstance method to control the instantiation and ensure only one instance exists.*

    
