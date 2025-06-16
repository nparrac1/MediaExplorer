<?php

use App\Http\Controllers\Api\CategoryController;
use App\Http\Controllers\Api\ContentController;
use Illuminate\Support\Facades\Route;

Route::apiResource('categories', CategoryController::class);
Route::apiResource('contents', ContentController::class);