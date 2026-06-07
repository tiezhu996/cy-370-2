# 图书馆座位预约管理系统

面向高校图书馆，提供座位可视化预约、签到管理和学习小组研讨间预约的智能化系统。

## Docker Compose 快速启动

首次启动前复制环境变量文件：

```bash
cp .env.example .env
docker compose up -d
```

访问地址：

- 前端：http://localhost:28510
- 后端健康检查：http://localhost:29510/health
- API 示例：http://localhost:28510/api/overview

## 项目主要功能

- 楼层座位图可视化：以楼层平面图展示所有座位分布，区分普通座位、靠窗座位、带电源座位，用颜色标识空闲/已约/使用中状态。
- 按时段预约与选座：用户选择日期和时段（最小单位30分钟或1小时），在座位图上点选空闲座位，系统自动校验时段冲突，预约成功生成二维码。
- 扫码签到与违规释放：预约开始前后15分钟内扫码签到，超时未签到自动释放座位并标记违约，使用结束需扫码签退，记录实际使用时长。
- 座位偏好记忆：系统自动记录用户历史预约偏好（如常选楼层、靠窗座位），下次预约时优先推荐相似座位，提升预约效率。
- 学习小组研讨间预约：提供独立研讨间（4-8人）预约功能，发起人邀请组员加入，预约成功后所有组员收到通知，研讨间配备白板和投影设备记录。

## 本地开发方式

前端：

```bash
cd frontend
npm install
npm run dev
```

后端：

```bash
cd backend
mvn spring-boot:run
```

## 技术栈

| 分层 | 技术 |
| --- | --- |
| 前端 | Vue 3 + TypeScript、Element Plus、Vite |
| 后端 | Spring Boot + Java |
| 数据库 | MySQL 8.0 |
| 认证 | JWT |
| 依赖 | JPA（Hibernate）、Maven |

## 项目目录结构

```text
.
├── backend/              # 后端服务
├── database/             # 数据库脚本
├── frontend/             # 前端应用
├── docker-compose.yml    # 一键部署编排
├── .env.example          # 环境变量示例
└── README.md
```

## 环境变量说明

| 变量 | 说明 | 默认值 |
| --- | --- | --- |
| COMPOSE_PROJECT_NAME | Compose 项目名，避免中文目录名导致项目名为空 | ldlibseat |
| DB_NAME | 数据库名称 | app |
| DB_USER | 数据库用户 | app |
| DB_PASSWORD | 数据库密码 | app_pwd |
| DB_ROOT_PASSWORD | 数据库 root 密码 | root_pwd |
| JWT_SECRET | JWT 签名密钥 | change_me_to_a_long_random_string |
| FRONTEND_PORT | 前端宿主机端口 | 28510 |
| BACKEND_PORT | 后端宿主机端口 | 29510 |
| DB_PORT | 数据库宿主机端口 | 3306 |

## Docker 部署说明

- 使用 `docker compose up -d` 启动，不需要额外传入 `-p`。
- `docker-compose.yml` 顶层已声明 `name: ldlibseat`，并且 `.env` 包含 `COMPOSE_PROJECT_NAME=ldlibseat`，可在中文目录名下启动。
- 数据库数据保存在命名卷 `db_data` 中，不依赖当前目录名。
- 前端容器由 Nginx 托管静态资源，并把 `/api/` 反向代理到 `backend:29510`。
- 若本地端口冲突，可修改 `.env` 中的 `FRONTEND_PORT`、`BACKEND_PORT`、`DB_PORT`。

常用命令：

```bash
docker compose config --quiet
docker compose ps
docker compose down
```

## License

MIT
