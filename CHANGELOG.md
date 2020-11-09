# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.4.0] - 2020-11-09

### Added
- `<ddcme:entityDialog />` Facet API for using entity input dialog declaratively via XML

## [0.3.0] - 2020-03-05

### Dependencies
- CUBA 7.2.x

## [0.2.1] - 2019-11-07

### Removed
- removed dummy entity in the jar file

## [0.2.0] - 2019-11-07

### Changed
- `MetadataDialogs` is renamed to `EntityDialogs`
- `MetaPropertyInputParameter` is renamed to `EntityAttributeInputParameter`

### Added
- `EntityDataProvider` API (`de.diedavids.cuba.metadataextensions.dataprovider.EntityDataProvider`) to retrieve information about entities for common UI use-cases
- Mapped Superclasses for easy creation of an entity that is within the scope of a particular entity(-attribute). The following abstractions are available:
  - `EntityAwareStandardEntity` mapped superclass 
  - `EntityAware` interface 
  - `EntityAttributeAwareStandardEntity` mapped superclass 
  - `EntityAttributeAware` interface 
- non-persistent entity to represent a `MetaClass`: `MetaClassEntity` that can be used within data containers to e.g. show a list of all available Entities in the system


## [0.1.0] - 2019-10-31

### Dependencies
- CUBA 7.1.x

### Added
- `MetadataDialogs` API to create a metadata aware input dialog for meta properties


