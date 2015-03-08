/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE, ElementType.METHOD })
@GwtCompatible
public @interface GwtCompatible {

  /**
   * When {@code true}, the annotated type or the type of the method return
   * value is GWT serializable.
   *
   * @see <a href="http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&t=DevGuideSerializableTypes">
   *     Documentation about GWT serialization</a>
   */
  boolean serializable() default false;

  /**
   * When {@code true}, the annotated type is emulated in GWT. The emulated
   * source (also known as super-source) is different from the implementation
   * used by the JVM.
   *
   * @see <a href="http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&t=DevGuideModuleXml">
   *     Documentation about GWT emulated source</a>
   */
  boolean emulated() default false;
}
