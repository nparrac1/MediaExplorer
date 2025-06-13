<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\CategoryController;
use App\Http\Controllers\Api\ContentController;

Route::prefix('v1')->group(function () {
    // Categories
    Route::apiResource('categories', CategoryController::class);
    
    // Contents
    Route::apiResource('contents', ContentController::class);
    Route::get('categories/{categoryId}/contents', [ContentController::class, 'getByCategory']);
});