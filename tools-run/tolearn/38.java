/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.launching;

import org.eclipse.jdt.core.IClasspathEntry;

/**
 * Optional enhancements to {@link IRuntimeClasspathEntryResolver}.
 * <p>
 * Clients may implement this interface.
 * </p>
 * @since 3.2
 */
public interface IRuntimeClasspathEntryResolver2 extends IRuntimeClasspathEntryResolver {

    /**
	 * Returns whether the given classpath entry references a VM install.
	 * 
	 * @param entry classpath entry
	 * @return whether the given classpath entry references a VM install
	 */
    public boolean isVMInstallReference(IClasspathEntry entry);
}
