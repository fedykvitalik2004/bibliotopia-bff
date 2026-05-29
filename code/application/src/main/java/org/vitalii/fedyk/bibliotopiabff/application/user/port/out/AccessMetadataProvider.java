package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.AccessNames;

public interface AccessMetadataProvider {
  AccessNames getAccessNames(Set<Long> roleIds, Set<Long> permissionIds);
}
