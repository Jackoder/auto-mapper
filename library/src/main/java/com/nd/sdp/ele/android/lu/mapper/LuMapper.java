package com.nd.sdp.ele.android.lu.mapper;

import com.nd.sdp.ele.android.lu.Lu;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jackoder
 * @version 2016/11/24
 */
public class LuMapper {

    public static final String SUFFIX = "$Mapper";

    private static LuMapper sMapper;

    private Map<Class, IMapper> mMapperSet;

    private LuMapper() {
        mMapperSet = new HashMap<>();
    }

    public static Lu map(Object target) {
        if (sMapper == null) {
            synchronized (LuMapper.class) {
                if (sMapper == null) {
                    sMapper = new LuMapper();
                }
            }
        }
        IMapper mapper;
        if (target != null && (mapper = sMapper.findMapper(target.getClass())) != null) {
            return mapper.map(target);
        }
        return null;
    }

    private IMapper findMapper(Class targetClazz) {
        if (mMapperSet.containsKey(targetClazz)) {
            return mMapperSet.get(targetClazz);
        } else {
            try {
                Class mapperClazz = Class.forName(targetClazz.getName() + SUFFIX);
                IMapper mapper = (IMapper) mapperClazz.newInstance();
                mMapperSet.put(targetClazz, mapper);
                return mapper;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
