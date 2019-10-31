[![Build Status](https://travis-ci.com/mariodavid/cuba-component-metadata-extensions.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-metadata-extensions)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-metadata-extensions/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-metadata-extensions/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# CUBA Platform Application Component - Metadata Extensions

CUBA component that provides common cases for metadata interactions in CUBA


Table of Contents
=================

  * [Installation](#installation)
  * [Supported DBMS](#supported-dbms)
  * [Using the application component](#using-the-application-component)


## Installation

1. `metadata-extensions` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/metadata-extensions)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.1.x            | 0.1.x          |


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
