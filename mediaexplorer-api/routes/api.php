<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\CategoryController;
use App\Http\Controllers\Api\ContentController;

// Agrupar todas las rutas bajo v1
Route::apiResource('categories', CategoryController::class);
Route::apiResource('contents', ContentController::class);

