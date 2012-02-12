jPropCompare
=============

jPropCompare is a utility package that assists in managing and comparing property files. Compare property files by key or value and check the symmetric difference, intersection and union of property files. Provide validation rules that ensure that property values for specific keys match the given regex.

jPropCompare allows the creation of Loading Strategies to allow more complex hierarchical property structures to be tested.

How to integrate jPropCompare
-------
### Unit Test
Integrate jPropCompare into your unit tests to fail a build when property entries fail validation or property entries found in development or user acceptance testing environments are not found in production environments.

### Command Line
Use jPropCompare as a command line tool to quickly compare property files from different environments.

### Maven Plugin 
A maven plugin has been requested, this will be coming soon

How to use jPropCompare
-------
jPropCompare uses the concept of `Actions` to state how the property files should be compared. Available actions currently include:

* Symmetric difference in property name: Finds all property keys that are unique to a property file. This action is useful for comparing property files between each environment, where keys remain constant but the value changes
* Symmetric difference in property value: Finds all property keys that are either unique to a property file or where the same property key differ in value
* Intersection of values: Finds all property keys that are in both property files and have the same value.
* Union of names: Feature request, coming soon.
* Union of values: Feature request, coming soon.





