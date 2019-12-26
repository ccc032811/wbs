/*package com.neefull.fsp.web.qff.aaa;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neefull.fsp.web.common.controller.BaseController;
import com.neefull.fsp.web.common.entity.FebsResponse;
import com.neefull.fsp.web.common.exception.FebsException;
import com.neefull.fsp.web.qff.entity.Query;
import com.neefull.fsp.web.qff.aaa.Wrapper;
import com.neefull.fsp.web.qff.aaa.IWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


*//**
 * 包装QFF操作
 *
 * @Author: chengchengchu
 * @Date: 2019/12/6  18:50
 *//*


@RestController
@RequestMapping("/wrapper")
public class WrapperController extends BaseController {

    @Autowired
    private IWrapperService wrapperService;


    *//**新增包装QFF操作
     * @param wrapper
     * @return
     * @throws FebsException
     *//*

    @PostMapping("/add")
    public FebsResponse addWrapper(Wrapper wrapper) throws FebsException {
        Integer count = wrapperService.addWrapper(wrapper);
        if(count!=1){
            throw new FebsException("新增包装QFF操作成功");
        }
        return new FebsResponse().success();
    }

    *//**修改包装QFF操作
     * @param wrapper
     * @return
     * @throws FebsException
     *//*

    @PostMapping("edit")
    public FebsResponse editWrapper(Wrapper wrapper) throws FebsException {
        Integer count = wrapperService.editWrapper(wrapper);
        if(count!=1){
            throw new FebsException("修改包装QFF操作失败");
        }
        return new FebsResponse().success();
    }

    *//**查询包装QFF
     * @param query
     * @return
     *//*

    @PostMapping
    public FebsResponse getWrapperPage(Query query){
        IPage<Wrapper> pageInfo = wrapperService.getWrapperPage(query);
        Map<String, Object> dataTable = getDataTable(pageInfo);
        return new FebsResponse().success().data(dataTable);
    }

    *//**删除包装QFF
     * @param number
     * @return
     * @throws FebsException
     *//*

    @GetMapping("/deleteWrapper")
    public FebsResponse deleteWrapper(@RequestParam("number")String number) throws FebsException {
        Integer count = wrapperService.deleteWrapper(number);
        if(count !=1){
            throw new FebsException("删除包装QFF失败");
        }
        return new FebsResponse().success();
    }

    *//**查询包装QFF
     * @param number
     * @return
     * @throws FebsException
     *//*

    @GetMapping("/queryWrapper")
    public FebsResponse queryWrapperByNumber(@RequestParam("number")String number) throws FebsException {
        Wrapper wrapper = wrapperService.queryWrapperByNumber(number);
        if(wrapper==null){
            throw new FebsException("查询包装QFF失败");
        }
        return new FebsResponse().success().data(wrapper);
    }


}*/
