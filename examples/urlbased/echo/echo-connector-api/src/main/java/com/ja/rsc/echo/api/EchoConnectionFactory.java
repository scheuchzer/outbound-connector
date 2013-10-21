/*
Copyright 2013 Thomas Scheuchzer, java-adventures.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.ja.rsc.echo.api;

import com.ja.rsc.api.ConnectionFactory;

/**
 * The {@link EchoConnectionFactory} class is the Resource that you'll inject into your code
 * with the @Resource annotation. 
 * 
 * @author Thomas Scheuchzer, java-adventures.com
 * 
 */
public interface EchoConnectionFactory extends ConnectionFactory<EchoConnection> {

}
