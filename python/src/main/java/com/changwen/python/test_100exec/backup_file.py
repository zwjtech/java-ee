# !/usr/bin/python
# coding=utf-8
import time
import datetime
import os
import logging
import sys
import getopt

# 配置日志信息
logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S', filename='backup_file.log', filemode='w')
# 定义一个Handler打印INFO及以上级别的日志到sys.stderr
console = logging.StreamHandler()
# 设置日志打印格式
formatter = logging.Formatter('%(name)-12s: %(levelname)-8s %(message)s')
console.setFormatter(formatter)
# 将定义好的console日志handler添加到root logger
logging.getLogger('').addHandler(console)


def run_task(abs_file_path, timing_time):
    """定时备份指定的文件"""
    logging.info("开始执行任务")
    dir_path = abs_file_path[0:abs_file_path.rfind('/', 1) + 1]  # 获取文件路径

    #    file_name = abs_file_path.split("/")[-1]
    file_name = abs_file_path[abs_file_path.rindex("/") + 1:]  # 获取文件名， 这种效率更高

    timing_time = timing_time.strftime("%Y%m%d%H%M%S")
    new_file_name = dir_path + timing_time + '_' + file_name + '.bak'  # 重命名文件

    os.system('cp ' + abs_file_path + ' ' + new_file_name)  # 执行操作
    logging.info("执行任务完成！")


def backup_file(abs_file_path):
    logging.info("abs_file_path: " + abs_file_path)

    is_file_exist = os.path.exists(abs_file_path)  # 判断文件是否存在
    if is_file_exist == 0:
        logging.info("文件不存在")
        return

    # 定时时间为次日凌晨1点
    # timing_time = datetime.datetime.replace(datetime.datetime.now() + datetime.timedelta(days=1), hour=1, minute=0, second=0)

    # 测试用的定时时间，当前时间后10s
    timing_time = datetime.datetime.replace(datetime.datetime.now() + datetime.timedelta(seconds=10))
    logging.info(timing_time)

    while True:
        sleep_time = timing_time - datetime.datetime.now()  # 睡眠时间
        time.sleep(sleep_time.seconds)

        run_task(abs_file_path, timing_time)
        timing_time = timing_time + datetime.timedelta(seconds=10)
        # logging.info("下一次执行任务时间："+timing_time)


if __name__ == '__main__':
    # sys.argv[0] # 脚本名
    try:
        opts, args = getopt.getopt(sys.argv[1:], "hf:h:")
    except Exception, e:  # python3.0   except (Exception) as e:
        logging.info("参数输入出错")
        print("-p  必选  要定时备份文件的绝对路径 \n-h  可选  帮助 ")
        sys.exit()

    file_path = ""
    for op, value in opts:
        if op == "-f":
            file_path = value

        elif op == "-h":
            print("-p  必选  要定时备份文件的绝对路径 \n-h  可选  帮助 ")
            sys.exit()

    backup_file(file_path)
