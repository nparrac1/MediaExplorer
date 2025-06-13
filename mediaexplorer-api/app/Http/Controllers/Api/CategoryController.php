<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Category;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;

class CategoryController extends Controller
{
    public function index(): JsonResponse
    {
        $categories = Category::withCount('contents')->get();
        return response()->json($categories);
    }

    public function show($id): JsonResponse
    {
        $category = Category::with('contents')->find($id);
        
        if (!$category) {
            return response()->json(['error' => 'Category not found'], 404);
        }
        
        return response()->json($category);
    }

    public function store(Request $request): JsonResponse
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'description' => 'nullable|string',
            'imageUri' => 'nullable|string'
        ]);

        $category = Category::create($request->all());
        return response()->json($category, 201);
    }

    public function update(Request $request, $id): JsonResponse
    {
        $category = Category::find($id);
        
        if (!$category) {
            return response()->json(['error' => 'Category not found'], 404);
        }

        $request->validate([
            'name' => 'required|string|max:255',
            'description' => 'nullable|string',
            'imageUri' => 'nullable|string'
        ]);

        $category->update($request->all());
        return response()->json($category);
    }

    public function destroy($id): JsonResponse
    {
        $category = Category::find($id);
        
        if (!$category) {
            return response()->json(['error' => 'Category not found'], 404);
        }

        $category->delete();
        return response()->json(['message' => 'Category deleted successfully']);
    }
}