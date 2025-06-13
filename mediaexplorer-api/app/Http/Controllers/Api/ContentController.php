<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Content;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;

class ContentController extends Controller
{
    public function index(): JsonResponse
    {
        $contents = Content::with('category')->get();
        return response()->json($contents);
    }

    public function show($id): JsonResponse
    {
        $content = Content::with('category')->find($id);
        
        if (!$content) {
            return response()->json(['error' => 'Content not found'], 404);
        }
        
        return response()->json($content);
    }

    public function store(Request $request): JsonResponse
    {
        $request->validate([
            'title' => 'required|string|max:255',
            'tipo' => 'required|string|max:255',
            'description' => 'nullable|string',
            'imagen' => 'nullable|string',
            'categoriaId' => 'required|exists:categories,id'
        ]);

        $content = Content::create($request->all());
        return response()->json($content, 201);
    }

    public function update(Request $request, $id): JsonResponse
    {
        $content = Content::find($id);
        
        if (!$content) {
            return response()->json(['error' => 'Content not found'], 404);
        }

        $request->validate([
            'title' => 'required|string|max:255',
            'tipo' => 'required|string|max:255',
            'description' => 'nullable|string',
            'imagen' => 'nullable|string',
            'categoriaId' => 'required|exists:categories,id'
        ]);

        $content->update($request->all());
        return response()->json($content);
    }

    public function destroy($id): JsonResponse
    {
        $content = Content::find($id);
        
        if (!$content) {
            return response()->json(['error' => 'Content not found'], 404);
        }

        $content->delete();
        return response()->json(['message' => 'Content deleted successfully']);
    }

    public function getByCategory($categoryId): JsonResponse
    {
        $contents = Content::where('categoriaId', $categoryId)->get();
        return response()->json($contents);
    }
}