# 基于Spring的企业数字身份管控平台（IAM）设计与实现

## 数据库设计

### 数据库创建
创建数据库 `iam`。

### 用户表
`user` 表存储系统中用户的信息。

| 字段名称    | 数据类型     | 描述                     |
|-------------|--------------|--------------------------|
| id          | varchar(255) | 主键，用户登录ID         |
| name        | varchar(255) | 用户名称                 |
| group_id    | varchar(255) | 外键，引用 `user_group` 表 |
| status      | varchar(255) | 用户状态（未激活、活跃、禁用） |
| email       | varchar(255) | 用户邮箱                 |
| create_time | timestamp    | 记录创建时间             |
| update_time | timestamp    | 记录更新时间             |

### 等待激活用户表
`wait_active_user` 表存储等待激活的用户信息。

| 字段名称    | 数据类型     | 描述                     |
|-------------|--------------|--------------------------|
| id          | varchar(255) | 主键，用户登录ID         |
| active_code | varchar(255) | 激活码                   |
| expire_time | timestamp    | 激活码过期时间           |

### 用户组表
`user_group` 表存储用户组的信息。

| 字段名称    | 数据类型     | 描述                     |
|-------------|--------------|--------------------------|
| id          | varchar(255) | 主键，用户组ID           |
| name        | varchar(255) | 用户组名称               |
| description | varchar(255) | 用户组描述               |
| create_time | timestamp    | 记录创建时间             |
| update_time | timestamp    | 记录更新时间             |

### 应用表
`application` 表存储应用的信息。

| 字段名称    | 数据类型     | 描述                     |
|-------------|--------------|--------------------------|
| id          | varchar(255) | 主键，应用ID             |
| name        | varchar(255) | 应用名称                 |
| description | varchar(255) | 应用描述                 |
| create_time | timestamp    | 记录创建时间             |
| update_time | timestamp    | 记录更新时间             |

### 用户组应用关联表
`user_group_application` 表存储用户组与应用的关联信息。

| 字段名称      | 数据类型     | 描述                     |
|---------------|--------------|--------------------------|
| group_id      | varchar(255) | 外键，引用 `user_group` 表 |
| application_id| varchar(255) | 外键，引用 `application` 表 |
| primary key   | (group_id, application_id) | 复合主键           |