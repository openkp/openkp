/**
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
package pl.openkp.testutils;

import java.io.File;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class ArchiveHelper {

	public static WebArchive noClientApp() {
		return ShrinkWrap
				.create(WebArchive.class, "openkp.war")
				.addPackages(true, "pl.openkp")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("META-INF/load-script.sql",
						"META-INF/load-script.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml");
	}
	
	public static WebArchive fullApp() {
		WebArchive archive = noClientApp();
		ArchiveHelper.addFiles(archive, new File("src/main/webapp"));
//		System.out.println(archive.toString(Formatters.VERBOSE));
		return archive;
	}

	public static void addFiles(WebArchive war, File dir) {
		addFiles(war, dir, "");
	}

	private static void addFiles(WebArchive war, File dir, String context) {
		addFiles(war, dir, ArchivePaths.create(context));
	}

	private static void addFiles(WebArchive war, File dir, ArchivePath context) {
		if (!dir.isDirectory()) {
			throw new RuntimeException(dir.getAbsolutePath()
					+ " is not a directory");
		}

		for (File f : dir.listFiles()) {
			if (f.isFile()) {
				ArchivePath fileContext = ArchivePaths.create(context,
						f.getName());
				war.addAsWebResource(f, fileContext);
			} else {
				addFiles(war, f, ArchivePaths.create(context, f.getName()));
			}
		}
	}
}
