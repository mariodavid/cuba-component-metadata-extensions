[![Build Status](https://travis-ci.com/mariodavid/cuba-component-metadata-extensions.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-metadata-extensions)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-metadata-extensions/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-metadata-extensions/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# CUBA Component - Metadata Extensions

This application component provides extensions for different use-cases for metadata interactions in CUBA.


Table of Contents
=================

  * [Installation](#installation)
  * [Using the application component](#using-the-application-component)


## Installation

1. `metadata-extensions` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/metadata-extensions)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.2.x            | 0.3.x          |
| 7.1.x            | 0.1.x - 0.2.x  |


The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-metadata-extensions/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-metadata-extensions/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.metadataextensions`
* Artifact name: `metadataextensions-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.metadataextensions:metadataextensions-global:*addon-version*")
}
```


### CHANGELOG

Information on changes that happen through the different versions of the application component can be found in the [CHANGELOG](https://github.com/mariodavid/cuba-component-metadata-extensions/blob/master/CHANGELOG.md).
The Changelog also contains information about breaking changes and tips on how to resolve them.



## Using the application component


### Example usage
To see this application component in action, check out this example: [cuba-example-using-metadata-extensions](https://github.com/mariodavid/cuba-example-using-metadata-extensions).



### EntityDialogs API

The `EntityDialogs` API is an extension of the `Dialogs` API provided by CUBA since version 7.0.

EntityDialogs provides the ability to create a specific kind of `InputDialog` that allows to enter entity attributes. 
The method `createMetadataInputDialog` allows to specific one or multiple entity attributes. 
Based on the type that is defined in the Entity, the `InputDialog` will contain the correct input fields.

Additionally the values can be bound to a particular entity instance.

The API mirrors the style of the existing `Dialogs` API. An example usage of this method looks like this:

```
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import de.diedavids.cuba.metadataextensions.EntityDialogs;

import static entityAttributeParameter;

public class CustomerBrowse extends StandardLookup<Customer> {

    @Inject
    protected EntityDialogs entityDialogs;

    @Subscribe("customersTable.quickChange")
    protected void onCustomersTableQuickChange(Action.ActionPerformedEvent event) {

        Customer customer = customersTable.getSingleSelected();

        entityDialogs.createEntityInputDialog(this, Customer.class)
            .withEntity(customer)
            .withCaption(messageBundle.getMessage("change"))
            .withParameters(
                    entityAttributeParameter(Customer.class, "name")
                            .withAutoBinding(false),
                    entityAttributeParameter(Customer.class, "birthday")
                            .withAutoBinding(true)
            )
            .withCloseListener(closeEvent -> /* ... */)
            .show();
    }

}
```


The static method `entityAttributeParameter` is leveraged here to easily create `EntityAttributeInputParameter` instances.

The `EntityAttributeInputParameter` is very similar to the `InputParameter` from CUBA.

#### Auto binding
There is one attribute called `autoBinding`, which can be set through the corresponding builder method: `withAutoBinding`.
It means, that in case the the Dialog contains an entity reference through `withEntity(customer)`,
the value defined in the customer instance is bound to the input field of the Input Dialog.

For more information about the options for the parameter see [EntityAttributeInputParameter.java](https://github.com/mariodavid/cuba-component-metadata-extensions/blob/master/modules/gui/src/de/diedavids/cuba/metadataextensions/EntityAttributeInputParameter.java).


### EntityDataProvider API

The `de.diedavids.cuba.metadataextensions.dataprovider.EntityDataProvider` interface provides
some convenient methods to get information about Entities and its entity attributes.

It consists of the following APIs:

* `entitiesLookupFieldOptions`
* `entityAttributesLookupFieldOptions`
* `businessEntityAttributes`

Those methods allow to get Entity (attributes) in various forms.

This API is a combination of the already existing `Metadata`, `MetadataTools` and `MessageTools` APIs of CUBA.

### Metadata Storage for Entities

The metadata storage facilities for entities allow to easily reference meta classes and meta properties in an
entity. A common use-case for that is configuration entities that store a particular information on a
per-entity(-attribute) basis.

An example is an `DefaultValueConfiguration` Entity which stores default values for entity attributes.
Those default values should be assigned to a new instance of the entity when it is created. In order to store this
default value, it also has to reference the particular entity attribute for which this default value is defined.

This application component helps creating those kinds of entities by providing the following pieces:

#### Mapped Superclasses: `EntityAwareStandardEntity` and `EntityAttributeAwareStandardEntity`

There are two JPA entities defined as mapped superclasses that can be extended in the application entity:

* `EntityAwareStandardEntity` - Base class for Entities that reference a specific entity
* `EntityAttributeAwareStandardEntity` - Base class for Entities that reference a specific entity _attribute_

The entities pre populated attributes for storing a MetaClass (attribute: `entity`) and a MetaProperty (attribute: `entityAttribute`).

Those mapped superclasses extend CUBAs `StandardEntity`. In case the `StandardEntity` is not applicable in the target
application (e.g. because no soft-delete is required), the corresponding interfaces `EntityAware` & `EntityAttributeAware`
can be leveraged.

#### Custom Datatypes and Converters for Metadata

The application components provides support for defining both CUBA metadata interfaces directly as a datatype in a JPA entity:

##### Metadata Datatypes

* `de.diedavids.cuba.metadataextensions.datatype.MetaClassDatatype`: allows to use `com.haulmont.chile.core.model.MetaClass` directly as a datatype
* `de.diedavids.cuba.metadataextensions.datatype.MetaPropertyDatatype`: allows to use `com.haulmont.chile.core.model.MetaProperty` directly as a datatype


##### Metadata Converters

* `de.diedavids.cuba.metadataextensions.converter.MetaClassConverter`: allows to convert a `com.haulmont.chile.core.model.MetaClass` to a column in the database
* `de.diedavids.cuba.metadataextensions.converter.MetaPropertyConverter`: allows to use `com.haulmont.chile.core.model.MetaProperty` to a column in the database


#### Non-persistent Entity representing a MetaClass

Sometimes it is necessary to have an Entity that represents a MetaClass. This is useful when e.g. a list of all existing 
MetaClasses in the application should be rendered. For this use-case this application component contains the entity
`de.diedavids.cuba.metadataextensions.entity.MetaClassEntity` that serves that purpose.


