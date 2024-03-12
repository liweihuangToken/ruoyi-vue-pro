<template>
	<view class="backgroud">
		<uni-section title="查询条件" type="line">
			<uni-easyinput class="uni-mt-10" :focus="focusFlag" v-model="searchValue" placeholder="请手动输入或扫描输入订单编码" @confirm="search"/>
		</uni-section>
		<div>
			<uni-section title="货物编码" type="line" class="uni-margin-top">
				<uni-easyinput :focus="focusFlag2" v-model="goodsCode" placeholder="请手动输入或扫描输入货品编码"/>
			</uni-section>
			<uni-section title="主体信息" type="line">
				<uni-card title="标签信息" padding="10px 0" >
					<image style="width: 100%;" :src="cover"/>
				</uni-card>
			</uni-section>
			<uni-section title="状态跟踪" type="line" padding>
				<uni-steps :options="list2" active-color="#007AFF" :active="active" direction="column" />
			</uni-section>
			<uni-section title="所属上游今日综合信息" type="line" padding class="uni-list-item__extra-text">
				<uni-card title="结款信息" padding="10px 0" >
					<uni-list>
						<uni-list-item :show-extra-icon="true" :border="true" :extra-icon="extraIcon_fee" title="结款金额" :rightText="settlementAmont" />
						<uni-list-item :border="false" title="待处理订单数" :rightText="untreatedCount" />
						<uni-list-item :border="false" title="入库订单总额(付)" :rightText="putStorageOrderTotalAmont" />
						<uni-list-item :border="false" title="退货订单总额(收)" :rightText="retureOrderTotalAmont" />
					</uni-list>
				</uni-card>
				<uni-card title="入库信息" padding="10px 0" >
					<uni-list>
						<uni-list-item :border="false" title="订单总数" :rightText="orderTotalCount" />
						<uni-list-item :border="false" title="已入库订单数" :rightText="putStorageCount" />
						<uni-list-item :border="false" title="无货订单数" :rightText="noPutStorageCount" />
						<uni-list-item :border="false" title="待处理订单数" :rightText="untreatedCount" />
						<uni-list-item :border="false" title="入库订单总额(付)" :rightText="putStorageOrderTotalAmont" />
						<uni-list-item :show-extra-icon="true" showArrow :extra-icon="extraIcon_come" title="点击查看上游订单入库明细" />
					</uni-list>
				</uni-card>
				<uni-card title="退单信息" padding="10px 0" >
					<uni-list>
						<uni-list-item :border="false" title="退货订单总数" :rightText="retureOrderTotalCount" />
						<uni-list-item :border="false" title="退货订单总额(收)" :rightText="retureOrderTotalAmont" />
						<uni-list-item :show-extra-icon="true" showArrow :extra-icon="extraIcon_out" title="点击查看上游订单退货明细" />
					</uni-list>
				</uni-card>
			</uni-section>
			<div class="uni-button-position">
				<button type="default" class="uni-button" @click="reset">重置</button>
				<button type="primary" class="uni-button" :loading="isLoading" @click="upateStatus('2')">无货</button>
				<button type="warn" class="uni-button" :loading="isLoading2" @click="upateStatus('1')">普通入库</button>
				<button type="warn" class="uni-button" :loading="isLoading3">付款入库</button>
				<button type="primary" class="uni-button" @click="barcode">扫码测试</button>
				<!-- <button type="warn" class="uni-button">撤销入库</button> -->
			</div>
		</div>
		<view>
			<!-- 提示信息弹窗 -->
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000" align="center"></uni-popup-message>
			</uni-popup>
		</view>
	</view>
	

</template>
<script>
	import { getOrderDetailInfoByCode, updateOrderStatus } from "@/api/orders/orders"
	import { DICT_TYPE, getDictDataLabel } from "@/utils/dict";
	import { parseTime } from "@/utils/ruoyi";
	
	export default {
		data() {
			return {
				type: 'center',
				msgType: 'success',
				messageText: '这是一条成功提示',
				value: '',
				focusFlag: true,
				focusFlag2: false,
				isLoading: false,
				isLoading2: false,
				isLoading3: false,
				goodsCode: '',
				searchValue:'',
				cover: '',
				form: {},
				active: 0,
				list2: [],
				extraIcon_come: {
					color: '#2979ff',
					size: '22',
					type: 'download-filled'
				},
				extraIcon_out: {
					color: '#2979ff',
					size: '22',
					type: 'upload-filled'
				},
				extraIcon_fee: {
					color: '#2979ff',
					size: '22',
					type: 'cart-filled'
				},
				orderTotalCount: null,
				putStorageCount: null,
				noPutStorageCount: null,
				untreatedCount: null,
				putStorageOrderTotalAmont: null,
				retureOrderTotalCount: null,
				retureOrderTotalAmont: null,
				settlementAmont: null,
			}
		},
		computed: {
		},
		onLoad() {
			uni.hideKeyboard();
		},
		methods: {
			reset() {
				uni.hideKeyboard();
				this.goodsCode = '';
				this.cover = '';
				this.searchValue = '';
				this.form = {};
				this.list2 = [];
				this.orderTotalCount = null;
				this.putStorageCount = null;
				this.noPutStorageCount = null;
				this.untreatedCount = null;
				this.putStorageOrderTotalAmont = null;
				this.focusFlag = false;
				this.focusFlag2 = false;
				this.$nextTick(function() {
					this.focusFlag = true;
				});
				
			},
			search(res) {
				uni.hideKeyboard();
				if('' == res.value){
					this.msgType = "warn";
					this.messageText = '搜索编码不能为空';
					this.$refs.message.open();
					this.reset();
					return;
				}
				this.searchByCode();
			},
			searchByCode() {
				// 延迟500ms 等待搜索框编码输入完成
				setTimeout(() => {
					getOrderDetailInfoByCode(this.searchValue).then(response => {
					  var row = response.data;
					  if(!row){
						  this.msgType = 'warn';
						  this.messageText = '未找到编号为："' + this.searchValue + '"的订单';
						  this.$refs.message.open();
						  this.reset();
						  return;
					  }
					  this.form = row;
					  this.cover = row.orderOnedimensionalCodePictureUrl;
					  this.goodsCode = row.goodsCode;
					  if(row.orderStatusTrackInfoDOList) {
						  row.orderStatusTrackInfoDOList.forEach((item, index) => {
						    item.title = this.getDictDataLabel(DICT_TYPE.DISTRIBUTION_ORDER_STATUS, item.orderAfterChangeStatus);
							item.desc = parseTime(item.createTime);
						  });
						  this.list2 = row.orderStatusTrackInfoDOList;
					  }
					  if(row.orderDetailStatisticsInfoVO) {
						  this.orderTotalCount = row.orderDetailStatisticsInfoVO.orderTotalCount + '';
						  this.putStorageCount = row.orderDetailStatisticsInfoVO.putStorageCount + '';
						  this.noPutStorageCount = row.orderDetailStatisticsInfoVO.noPutStorageCount + '';
						  this.untreatedCount = row.orderDetailStatisticsInfoVO.untreatedCount + '';
						  this.putStorageOrderTotalAmont = row.orderDetailStatisticsInfoVO.putStorageOrderTotalAmont + '';
						  this.settlementAmont = (row.orderDetailStatisticsInfoVO.putStorageOrderTotalAmont - 0) + ''; 
					  }
					  this.focusFlag = false;
					  this.focusFlag2 = false;
					  this.$nextTick(function() {
						this.focusFlag2 = true;
					  });
					});
				}, 500);
			},
			upateStatus(status) {
				this.form.orderStatus = status;
				if (this.form.id != null) {
				  if(status == '1') {
					if(null == this.goodsCode || '' == this.goodsCode) {
						this.msgType = 'error';
						this.messageText = '入库时货物编码不能为空';
						this.$refs.message.open();
						this.focusFlag = false;
						this.focusFlag2 = false;
						this.$nextTick(function() {
							this.focusFlag2 = true;
						});
						return;
					}
					this.isLoading2 = true;
					this.form.goodsCode = this.goodsCode;
				  }
				  if(status == '2') {
					this.isLoading = true;
				  }
				  updateOrderStatus(this.form).then(response => {
					   this.searchByCode();
					   this.isLoading = false;
					   this.isLoading2 = true;
					   this.$nextTick(function() {
					   	this.isLoading2 = false;
					   });
					   this.msgType = 'success';
					   this.messageText = '操作成功';
					   this.$refs.message.open();
				  });
				  return;
				}
			},
			barcode() {
				// 调起条码扫描
				uni.scanCode({
					scanType: ['barCode'],
					success: function (res) {
						this.searchValue = res.result;
						this.goodsCode = res.result;
						console.log('条码内容：' + res.result);
					}
				});
			},
		}
	}
</script>
<style>

	.backgroud {
		padding: 15px;
		background-color: #fff;
		height: 100%;
	}

	.uni-button {
	    position: relative;
	    margin-top: 25px;
		width: 120px;
	}
	
	.uni-button-position {
		position: fixed;
		width: 85px;
		bottom: 0;
		margin: 70% 0;
		opacity:0.8;
	}
	
	.uni-mt-10 {
		margin-top: 0px;
	}
	
	/deep/ .uni-list-item__extra-text {
	    color: #101010;
	    font-size: 12px;
	}
</style>

