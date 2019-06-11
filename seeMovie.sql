-- --------------------------------------------------------
-- 主机:                           62.234.178.175
-- 服务器版本:                        5.7.23 - MySQL Community Server (GPL)
-- 服务器操作系统:                      linux-glibc2.12
-- HeidiSQL 版本:                  9.3.0.5104
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 see_movie.menu 结构
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '菜单主键',
  `menu_name` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `menu_href` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单链接(顶级可以为空)',
  `menu_parent_id` varchar(32) COLLATE utf8_bin DEFAULT '0' COMMENT '父级菜单id(默认0代表顶级菜单）',
  `menu_flag` varchar(1) COLLATE utf8_bin DEFAULT 'Y' COMMENT '菜单有效标志（Y/N）',
  `menu_icon` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的图标',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- 数据导出被取消选择。
-- 导出  表 see_movie.movie 结构
CREATE TABLE IF NOT EXISTS `movie` (
  `movie_id` varchar(32) NOT NULL COMMENT '主键',
  `category` varchar(50) DEFAULT 'teleplay' COMMENT '影片类型(teleplay电视剧  其他都是非电视剧）',
  `downLoad_href` varchar(300) DEFAULT NULL COMMENT '下载连接',
  `movie_name` varchar(200) DEFAULT NULL COMMENT '电影名字',
  `source` varchar(100) DEFAULT NULL COMMENT '数据来源',
  `img_url` varchar(150) DEFAULT NULL COMMENT '电影图片路径1',
  `img_url2` varchar(150) DEFAULT NULL COMMENT '电影图片路径2',
  `douban_score` decimal(10,1) DEFAULT '6.0' COMMENT '豆瓣评分',
  `produce_year` varchar(4) DEFAULT '' COMMENT '电影产生年代',
  `produce_country` varchar(10) DEFAULT '' COMMENT '电影产地',
  `describes` varchar(2500) DEFAULT NULL COMMENT '电影描述',
  `synchronous_flag` varchar(1) DEFAULT 'N' COMMENT '影片类型(电影/电视剧)同步标志(Y/N)',
  `synchronous_imgUrl_flage` varchar(1) DEFAULT 'N' COMMENT '影片图片链接同步标志(Y/N)',
  `insert_date` timestamp NULL DEFAULT NULL COMMENT '接入日期',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新日期',
  `search_score` int(6) DEFAULT NULL COMMENT '电影搜索指数',
  `see_or_downLoad_score` int(6) DEFAULT NULL COMMENT '电影查看详情(或者下载)指数',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  KEY `movie_id` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电影相关主表';

-- 数据导出被取消选择。
-- 导出  表 see_movie.movie_category 结构
CREATE TABLE IF NOT EXISTS `movie_category` (
  `movie_category_code` varchar(10) NOT NULL COMMENT '影片类型码值',
  `movie_category_name` varchar(10) NOT NULL COMMENT '影片类型',
  KEY `movie_category_code` (`movie_category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='影片类型表';

-- 数据导出被取消选择。
-- 导出  表 see_movie.system_log 结构
CREATE TABLE IF NOT EXISTS `system_log` (
  `log_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '日志主键',
  `log_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '日志名称',
  `log_content` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '日志详细信息',
  `log_level` varchar(1) COLLATE utf8_bin DEFAULT '1' COMMENT '日志等级（1普通2严重3致命）',
  `log_create_date` datetime DEFAULT NULL COMMENT '日志产生时间',
  KEY `log_id` (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统日志表';

-- 数据导出被取消选择。
-- 导出  表 see_movie.visit_user_info 结构
CREATE TABLE IF NOT EXISTS `visit_user_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名（用于记录后台管理入口操作人）',
  `source` varchar(1) DEFAULT '1' COMMENT '来源（1前台页面，2后端管理入口）',
  `ip` varchar(15) DEFAULT NULL COMMENT '访问者ip',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'ip地址所在省市区',
  `host_name` varchar(15) DEFAULT NULL COMMENT '访问主机名',
  `visit_date` datetime DEFAULT NULL COMMENT '访问日期',
  KEY `info_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录访问系统的用户信息';

-- 数据导出被取消选择。
-- 导出  表 see_movie.web_links 结构
CREATE TABLE IF NOT EXISTS `web_links` (
  `web_id` varchar(32) NOT NULL COMMENT '主键',
  `web_name` varchar(50) DEFAULT NULL COMMENT '网站名称',
  `web_link` varchar(100) NOT NULL COMMENT '网站链接',
  `crawl_flag` varchar(1) DEFAULT 'N' COMMENT '爬取标志(Y爬取/N不爬取)',
  `insert_date` datetime DEFAULT NULL COMMENT '增加链接时时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新链接状态时间',
  KEY `web_id` (`web_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='爬取数据的网站及其衔接';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
