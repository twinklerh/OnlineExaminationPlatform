<template>
  <el-col>
    <div class="text-div" @click="$router.push('/enterprise/')">在线应试平台</div>
    <el-divider class="el-divider"></el-divider>
    <el-menu class="aside-bar" default-active="1"
        text-color="black" active-text-color="rgb(64,158,255)" 
        background-color="red"> 
      <!-- 上面这一行的background-color是鼠标悬浮的颜色，css里的是背景色 -->
      <el-menu-item index="1" class="el-menu-item" @click="setNowPage('problems')">
        <el-icon><Files /></el-icon>
        <span>试题库</span>
      </el-menu-item>
      <el-menu-item index="2" class="el-menu-item" @click="setNowPage('addproblem')">
        <el-icon><CirclePlus /></el-icon>
        <span>试题录入</span>
      </el-menu-item>
      <el-menu-item index="3" class="el-menu-item" @click="setNowPage('getgrade')">
        <el-icon><Search /></el-icon>
        <span>成绩查询</span>
      </el-menu-item>
      <el-menu-item index="4" class="el-menu-item" @click="setNowPage('boardtest')">
        <el-icon><Calendar /></el-icon>
        <span>发布考试</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</template>

<script lang="ts" setup>
import { Files, CirclePlus, Search, Calendar } from '@element-plus/icons-vue'
import "@/css/asideBarStyle.css";
import { usePageStore } from '@/store/page';
const pageStore = usePageStore();
function setNowPage(page:string){
  pageStore.setNowPage(page);
}
</script>

<style scoped>
.aside-bar{
  background-color: var(--aside-bar-color);
  border: 0;
}
.text-div{
  background-color: var(--aside-bar-color);
  display: flex; /* 本行让span占满了父组件 */
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  height: 56px;
  cursor: pointer; /* 设置鼠标样式为手型 */
}
.el-divider{
  margin: 0;
  height: 0px;
}
.el-menu-item{
  border-radius: 8px;
  font-size: 16px;
  height: 60px;
}
.el-menu-item.is-active {
  background-color: rgb(236,245,255);
}
.el-menu-item:hover{
  background-color: transparent; /*取消鼠标悬浮时的背景色*/
  color: rgb(64,158,255)
}
</style>