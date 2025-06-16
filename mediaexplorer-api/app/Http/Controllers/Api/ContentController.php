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
    public function store(Request $request)
    {
        $validated = $request->validate([
            'title' => 'required|string|max:255',
            'tipo' => 'required|string',
            'description' => 'required|string',
            'imagen' => 'required|string',
            'categoria_id' => 'required|exists:categories,id'
        ]);

        // Si la imagen viene como base64, guárdala
        if (str_starts_with($validated['imagen'], 'data:image')) {
            $image = Image::make($validated['imagen']);
            $imagePath = 'images/' . time() . '.jpg';
            Storage::disk('public')->put($imagePath, $image->encode());
            $validated['imagen'] = Storage::url($imagePath);
        }

        $content = new Content($validated);
        $content->id = Str::uuid()->toString();
        $content->save();

        return $content;
    }

    public function update(Request $request, Content $content)
    {
        $validated = $request->validate([
            'title' => 'required|string|max:255',
            'tipo' => 'required|string',
            'description' => 'required|string',
            'imagen' => 'required|string',
            'categoria_id' => 'required|exists:categories,id'
        ]);

        // Si la imagen viene como base64, guárdala
        if (str_starts_with($validated['imagen'], 'data:image')) {
            $image = Image::make($validated['imagen']);
            $imagePath = 'images/' . time() . '.jpg';
            Storage::disk('public')->put($imagePath, $image->encode());
            $validated['imagen'] = Storage::url($imagePath);
        }

        $content->update($validated);
        return $content;
    }
}