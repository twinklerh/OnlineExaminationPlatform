<template>
    <el-form class="el-form">

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</el-col>
            <el-col :span="8">
                <el-input v-model="title" placeholder="标题" clearable />            
            </el-col>
        </el-row>

        <br>
        <el-row class="el-row-group">
            <el-col :span="2" class="el-col-text-pure">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;组：</el-col>
            <el-select class="el-select" v-model="groupSelect" placeholder="Select">
                <el-option :label="i" :value="i" v-for="(i,index) in groupStore.groupList" :key="index" />
            </el-select>
            <el-tooltip effect="dark" content="添加一个分组" placement="right-start">
                <el-icon class="el-icon" size="20"><CirclePlus @click="addNewGroup" /></el-icon>
            </el-tooltip>
        </el-row>

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure-descript">题目描述：</el-col>
            <el-col :span="16">
                <el-input class="el-input-descript" v-model="description" type="textarea" placeholder="题目描述" :autosize="{ minRows: 5, maxRows: 100 }" clearable />            
            </el-col>
        </el-row>

        <br>
        <el-row style="display: flex; align-items: center;">
            <el-col class="el-col-text-pure" :span="2">难易程度：</el-col>
            <el-col :span="13">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-radio-group v-model="radioSelectRank">
                    <el-radio label="未设置">暂不设置</el-radio>
                    <el-radio label="简单">简单</el-radio>
                    <el-radio label="一般">一般</el-radio>
                    <el-radio label="难">难</el-radio>
                </el-radio-group>
            </el-col>
        </el-row>

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure">批改类型：</el-col>
            <el-col :span="13">
                <el-select v-model="checkSelect">
                    <el-option label="自动批改" value="自动批改"  />
                    <el-option label="人工批改" value="人工批改"  />
                </el-select>
            </el-col>
        </el-row>
        
        <br>
        <el-row v-if="checkSelect==='mechine'">
            <el-col :span="2" class="el-col-text-pure-descript">正确答案：</el-col>
            <el-col :span="16">
                <el-input v-model="rightAnswer" :autosize="{ minRows: 4, maxRows: 100 }" type="textarea" placeholder="正确答案" />
            </el-col>
        </el-row>

        <el-row v-if="checkSelect==='human'">
            <el-col :span="2" class="el-col-text-pure">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件：</el-col>
            <el-col :span="13">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-radio-group v-model="appendix">
                    <el-radio label="false">无需附件</el-radio>
                    <el-radio label="true">可选附件</el-radio>
                </el-radio-group>
                <span v-if="appendix==='true'" style="color:rgb(163, 163, 0); font-size: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*单个附件大小不得超过10MB</span>
            </el-col>
        </el-row>
        <div style="margin-left: 770px; margin-top: 20px;">
            <el-button type="primary" @click="submit">提交</el-button>
        </div>
    </el-form>
    <AddingGroup ref="childRef"/>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';
import { CirclePlus } from '@element-plus/icons-vue'
import $ from 'jquery'
import { useUserStore } from '@/store/user';
import { useGroupStore } from '@/store/group';
import AddingGroup from '@/components/AddingGroup.vue';
import { ElMessage } from 'element-plus';

const groupStore = useGroupStore();

const userStore = useUserStore();
const title = ref('');
const groupSelect = ref('默认分组')
const description = ref('')
const radioSelectRank = ref('未设置')
const checkSelect = ref('自动批改')
const rightAnswer = ref('');
const appendix = ref('');

const childRef = ref();

groupStore.getAllGroups();

function addNewGroup() {
    childRef.value.changeDialogVisable();
}

watch(appendix, ()=>{
    console.log(appendix.value);
},{deep:true})

function submit(){
    $.ajax({
        url: 'http://localhost:3000/problem/submit/subjectivity/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + userStore.token,
        },
        data:{
            'title': title.value,
            'groupSelect': groupSelect.value,
            'description': description.value,
            'radioSelectRank': radioSelectRank.value,
            'checkSelect': checkSelect.value,
            'rightAnswer': rightAnswer.value,
            'appendix': appendix.value
        },// eslint-disable-next-line
        success: (resp:any)=>{
            ElMessage({message: "成功添加一个试题", type: 'success',});
        },
        error(){
            ElMessage.error('添加失败');
        }
    })
}

</script>

<style scoped>
.el-form{
    margin-top: 10px;
    margin-left: 30px;
}
.el-select{
    width: 200px;
}
.el-col-text-pure{
    display: flex;
    justify-content: right;
    align-items: center;
}
.el-col-text-pure-descript{
    display: flex;
    justify-content: right;
    align-items: top;
}
.el-input-descript{
    min-height: 50px;
}
.el-row-group{
    display: flex;
    align-items: center;
}
.el-icon{
    margin-left: 9px;
    color: blue;
}
.el-icon:hover{
    cursor: pointer;
}
</style>