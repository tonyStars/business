package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * <p>
 * ${table.comment} 服务实现类
 * </p>
 *
 * @author ${author}
 * @date ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} :  ServiceImpl<${table.mapperName},${entity}> {

}
<#else>
public class ${table.serviceImplName} extends ServiceImpl<${table.mapperName},${entity}> {

}
</#if>