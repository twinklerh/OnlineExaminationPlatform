<template>
  <div>
    <el-container class="el-container" >
      <el-aside class="el-aside"><AsideBar /></el-aside>
      <el-main class="el-main">
        <ProblemView  v-if="pageStore.nowPage==='problems'" />
        <AddProblemView v-if="pageStore.nowPage==='addproblem'" />
        <GetGradeView v-if="pageStore.nowPage==='getgrade'" />
        <AddExamView v-if="pageStore.nowPage==='boardtest'" />
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import AsideBar from '@/components/AsideBar.vue';
import ProblemView from './views/enterprise/ProblemView.vue';
import AddProblemView from './views/enterprise/AddProblemView.vue';
import GetGradeView from './views/enterprise/GetGradeView.vue';
import AddExamView from './views/enterprise/AddExamView.vue';
import { usePageStore } from './store/page';
const pageStore = usePageStore();
</script>

<style scoped>
.el-container{
  height: 800px;
}
.el-aside{
  width: 280px;
  background-color: var(--aside-bar-color);
}
</style>
