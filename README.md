# Cara radio 3

Read text messages sent to Radio 3; pulled from http://www.radio3.rai.it/radio3sms/smsradiotre.xml

# Get it on the Play Store

https://play.google.com/store/apps/details?id=it.edoput.cararadio3

# Build instruction

Android Studio should take care of everything; import the project, sync gradle and you can run the app from Android Studio

This app requires the SimpleXml parsing framework, as included in the Maven deps file. To remove conflict with the Android system you must include the lines

```
dependencies {
    compile('org.simpleframework:simple-xml:2.7.1') {
        exclude group: 'stax', module: 'stax-api'
	exclude group: 'xpp3', module: 'xpp3'
}
```

to your build.gradle file in the app directory.

# License

Copyright (C) 2016  Edoardo Putti

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
