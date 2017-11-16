[![Maven Central](https://maven-badges.herokuapp.com/maven-central/name.valery1707.validator/russian-requisites-validator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/name.valery1707.validator/russian-requisites-validator)
[![Javadoc](https://javadoc-emblem.rhcloud.com/doc/name.valery1707.validator/russian-requisites-validator/badge.svg)](http://www.javadoc.io/doc/name.valery1707.validator/russian-requisites-validator)
[![License](https://img.shields.io/github/license/valery1707/russian-requisites-validator.svg)](http://opensource.org/licenses/MIT)
[![OpenHUB](https://www.openhub.net/p/russian-requisites-validator/widgets/project_thin_badge.gif)](https://www.openhub.net/p/russian-requisites-validator)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fvalery1707%2Frussian-requisites-validator.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fvalery1707%2Frussian-requisites-validator?ref=badge_shield)

[![Travis CI](https://travis-ci.org/valery1707/russian-requisites-validator.svg?branch=master)](https://travis-ci.org/valery1707/russian-requisites-validator)

[![CoverAlls.io](https://coveralls.io/repos/valery1707/russian-requisites-validator/badge.svg?branch=master)](https://coveralls.io/r/valery1707/russian-requisites-validator)

[![Stories in Ready](https://badge.waffle.io/valery1707/russian-requisites-validator.svg?label=ready&title=Ready%20to%20work)](http://waffle.io/valery1707/russian-requisites-validator)
[![DevOps By Rultor.com](http://www.rultor.com/b/valery1707/russian-requisites-validator)](http://www.rultor.com/p/valery1707/russian-requisites-validator)

# russian-requisites-validator
Javax validation implementation for Russian requisites: ИНН, КПП, ОГРН

# Build

Build with code coverage information:
```bash
mvn clean test jacoco:report
```
See: `target/site/jacoco/index.html`

### [PITest](http://pitest.org/) mutation testing

```bash
mvn clean test org.pitest:pitest-maven:mutationCoverage
```
See: `target/pit-reports/*/index.html`


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fvalery1707%2Frussian-requisites-validator.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fvalery1707%2Frussian-requisites-validator?ref=badge_large)