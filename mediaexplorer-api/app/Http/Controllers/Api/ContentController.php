<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Content;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Intervention\Image\Facades\Image;
use Illuminate\Support\Facades\Storage;

class ContentController extends Controller
{
    public function index()
    {
        try {
            $contents = Content::all();
            return response()->json($contents, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function show(Content $content)
    {
        try {
            return response()->json($content, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function store(Request $request)
    {
        try {
            $validated = $request->validate([
                'title' => 'required|string|max:255',
                'tipo' => 'required|string',
                'description' => 'required|string',
                'imagen' => 'required|string',
                'categoria_id' => 'required|exists:categories,id'
            ]);

            // Procesar imagen base64 si es necesario
            if (str_starts_with($validated['imagen'], 'data:image')) {
                $image = Image::make($validated['imagen']);
                $imagePath = 'images/' . time() . '.jpg';
                Storage::disk('public')->put($imagePath, $image->encode());
                $validated['imagen'] = Storage::url($imagePath);
            }

            $content = new Content($validated);
            $content->id = Str::uuid()->toString();
            $content->save();

            return response()->json($content, 201);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function update(Request $request, Content $content)
    {
        try {
            $validated = $request->validate([
                'title' => 'required|string|max:255',
                'tipo' => 'required|string',
                'description' => 'required|string',
                'imagen' => 'required|string',
                'categoria_id' => 'required|exists:categories,id'
            ]);

            // Procesar imagen base64 si es necesario
            if (str_starts_with($validated['imagen'], 'data:image')) {
                $image = Image::make($validated['imagen']);
                $imagePath = 'images/' . time() . '.jpg';
                Storage::disk('public')->put($imagePath, $image->encode());
                $validated['imagen'] = Storage::url($imagePath);
            }

            $content->update($validated);
            return response()->json($content, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function destroy(Content $content)
    {
        try {
            $content->delete();
            return response()->json(['message' => 'Content deleted successfully'], 200);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }
}