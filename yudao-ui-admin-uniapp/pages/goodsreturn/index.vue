<template class="backgroud">
	<view class="backgroud">
		<uni-section title="退货查询条件" type="line">
			<uni-easyinput class="uni-mt-10" :focus="focusFlag" v-model="searchValue" placeholder="请手动输入或扫描输入订单编码" @confirm="search"/>
		</uni-section>
		<uni-section title="退货综合信息" type="line" padding class="uni-list-item__extra-text">
			<uni-card title="" padding="10px 0" >
				<uni-list>
					<uni-list-item :border="false" title="退货订单总数量" :rightText="retureOrderTotalNumber + ''" />
					<uni-list-item :border="false" title="退货订单总额(付)" :rightText="retureOrderTotalAmont + ''" />
				</uni-list>
			</uni-card>
		</uni-section>
		<div class='uni-section-backgroud'>
			<uni-section title="待退货列表" type="line" class="uni-margin-top">
				<uni-swipe-action ref="swipeAction">
					<uni-swipe-action-item
						v-for="(item, index) in swipeList"
						:right-options="options"
						:key="item.id"
						@change="swipeChange($event, index)"
						@click="swipeClick($event, index)"
					>
						<uni-list-item :title="item.goodsCode" :note="item.orderSalesAmount + ''"
							:thumb="item.orderGoodsPictureUrl"
							thumb-size="lg" :rightText="item.size" />
					</uni-swipe-action-item>
				</uni-swipe-action>
			</uni-section>
			<div class="uni-button-position">
				<button type="default" class="uni-button" @click="reset">重置</button>
				<button type="primary" class="uni-button" :loading="isLoading" @click="upateStatus('2')">退货</button>
				<button type="warn" class="uni-button" :loading="isLoading2" @click="upateStatus('1')">退货撤销</button>
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
				isLoading: false,
				isLoading2: false,
				isLoading3: false,
				searchValue:'',
				retureOrderTotalNumber: 0,
				retureOrderTotalAmont: 0,
				options: [{
						text: '取消'
					},
					{
						text: '删除',
						style: {
							backgroundColor: 'rgb(255,58,49)'
						}
					}
				],
				swipeList: [],
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
				this.searchValue = '';
				this.swipeList = [];
				this.retureOrderTotalNumber = 0;
			    this.retureOrderTotalAmont = 0;
				this.focusFlag = false;
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
						  // this.reset();
						  this.searchValue = '';
						  this.focusFlag = false;
						  this.$nextTick(function() {
							this.focusFlag = true;
						  });
						  return;
					  }
					  if(this.swipeList.length == 0) {
						  this.swipeList.push(row);
					  } else {
						  for (let index in this.swipeList) {
								if(row.orderCode == this.swipeList[index].orderCode) {
									this.msgType = 'warn';
									this.messageText = '编号为："' + this.searchValue + '"的订单已经在待退货列表中';
									this.$refs.message.open();
									// this.reset();
									this.searchValue = '';
									this.focusFlag = false;
									this.$nextTick(function() {
										this.focusFlag = true;
									});
									return;
								} else {
									this.swipeList.push(row);
								}
						  }
					  }
					  
					  var totalAmount = 0;
					  for (let index in this.swipeList) {
					  	totalAmount = totalAmount + this.swipeList[index].orderSalesAmount;
					  }
					  // console.log(this.swipeList)
					  this.retureOrderTotalNumber = this.swipeList.length;
					  this.retureOrderTotalAmont = totalAmount;
					  this.searchValue = '';
					  this.focusFlag = false;
					  this.$nextTick(function() {
						this.focusFlag = true;
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
			swipeChange(e, index) {
			},
			swipeClick(e, index) {
				let {
					content
				} = e;
				if (content.text === '删除') {
					uni.showModal({
						title: '提示',
						content: '是否删除',
						success: res => {
							if (res.confirm) {
								this.swipeList.splice(index, 1);
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
					});
				}
			},
		}
	}
</script>
<style>

	.backgroud {
		padding: 15px;
		background-color: #fff;
		min-height: 835px;
		height: 100%;
	}
	
	.uni-section-backgroud {
		border-radius: 30px;
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
	
	.content-box {
		flex: 1;
		/* #ifdef APP-NVUE */
		justify-content: center;
		/* #endif */
		height: 44px;
		line-height: 44px;
		padding: 0 15px;
		position: relative;
		background-color: #fff;
		border-bottom-color: #f5f5f5;
		border-bottom-width: 1px;
		border-bottom-style: solid;
	}

	.content-text {
		font-size: 15px;
	}

</style>

