package org.vitalii.fedyk.author.mapper;

import org.mapstruct.Mapper;
import org.vitalii.fedyk.author.Author;
import org.vitalii.fedyk.author.vo.CreateAuthorVO;

@Mapper(componentModel = "spring")
public interface AutMapper {
  Author fromVo(CreateAuthorVO createAuthorVO);
}
