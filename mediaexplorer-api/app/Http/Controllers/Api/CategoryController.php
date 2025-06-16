<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Category;
use Illuminate\Http\Request;
use Illuminate\Support\Str;
use Intervention\Image\Facades\Image;
use Illuminate\Support\Facades\Storage;

class CategoryController extends Controller
{
    public function index()
    {
        try {
            $categories = Category::all();
            return response()->json($categories, 200);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function store(Request $request)
    {
        try {
            $validated = $request->validate([
                'name' => 'required|string|max:255',
                'description' => 'required|string',
                'image_uri' => 'required|string'
            ]);

            if (str_starts_with($validated['image_uri'], 'data:image')) {
                $image = Image::make($validated['image_uri']);
                $imagePath = 'images/' . time() . '.jpg';
                Storage::disk('public')->put($imagePath, $image->encode());
                $validated['image_uri'] = Storage::url($imagePath);
            }

            $category = new Category($validated);
            $category->id = Str::uuid()->toString();
            $category->save();

            return response()->json($category, 201);
        } catch (\Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }
    }

    public function update(Request $request, Category $category)
    {
        $validated = $request->validate([
            'name' => 'required|string|max:255',
            'description' => 'required|string',
            'image_uri' => 'required|string'
        ]);

        // Si la imagen viene como base64, guárdala
        if (str_starts_with($validated['image_uri'], 'data:image')) {
            $image = Image::make($validated['image_uri']);
            $imagePath = 'images/' . time() . '.jpg';
            Storage::disk('public')->put($imagePath, $image->encode());
            $validated['image_uri'] = Storage::url($imagePath);
        }

        $category->update($validated);
        return $category;
    }
}