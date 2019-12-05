package org.cloudbus.cloudsim.container.core.redis;

public enum ERedisOperationType {
    Key,
    String,
    List,
    HashSet,
    Normal,
    Query,
    QueryMinimumRequest,
    QueryNotRequestRecently
}
