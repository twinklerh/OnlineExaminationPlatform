<template>
  <el-dialog v-model="dialogFormVisible" title="添加分组" width="500" :lock-scroll="false">
    <el-form>
      <el-form-item label="分组名" label-width="140px">
        <el-input v-model="group_name"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup props="props">
import { useGroupStore } from '@/store/group';
import { ref } from 'vue';

const groupStore = useGroupStore();

const dialogFormVisible = ref(false);
let group_name = ref('');

const changeDialogVisable = () => {
  dialogFormVisible.value = !dialogFormVisible.value;
}

function submit(){
  groupStore.AddNewGroup(group_name.value);
  changeDialogVisable();
}
// eslint-disable-next-line
defineExpose({ changeDialogVisable });
</script>

<style scoped>
</style>