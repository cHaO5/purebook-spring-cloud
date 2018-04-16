package com.purebook.backend.controller;

import com.purebook.backend.util.UnifiedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purebook.backend.result.JsonResult;
import com.purebook.backend.result.ResultCode;
import com.purebook.backend.service.TagService;

@RestController
@RequestMapping(value = "v1/tags")
public class TagController {

    @Autowired
    TagService tagService;

    //热门标签
    @RequestMapping
    public JsonResult getTags() {
        return UnifiedResult.result(tagService.getTag(), ResultCode.EXCEPTION);
    }
}
