# Acorn
Acorn project by Team Oak. Acorn is a pantry management system that allows users to keep track of the products they have in their pantry. In this way it can help prevent accidental food waste. Users can create their own pantries, add and manage products in their pantry, and keep track of expiration dates. Users can also share their pantry with others, in order to cooperate with friends, family or other groups such as fe. sports clubs. Acorn also has a shopping list feature that automatically generates a shopping list for a pantry based on what's currently in the pantry and the minimum stock requirements that the users' set for that pantry.

### Types of users
Systems Admin: has full authority on all aspects of the application.
Pantry Admin: can manage the contents of a pantry as well as the users of that pantry.
Acorn User: can create and manage the products of their pantries.

### Types of entities
ProductDefinition: describes a product that can be put in the pantry (it is not the actual item in your pantry, for example it cannot have an expiration date) 
Pantry: a digital container where users can add pantry products so that it reflects their physical pantry
PantryProduct: an item in your pantry 
AcornUser
PantryUser
Role: set for SysAdmins
Permission
