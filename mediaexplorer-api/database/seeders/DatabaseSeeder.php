<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Category;
use App\Models\Content;
use Illuminate\Support\Str;

class DatabaseSeeder extends Seeder
{
    public function run()
    {
        // Crear categorías
        $peliculas = Category::create([
            'id' => Str::uuid()->toString(),
            'name' => 'Películas',
            'description' => 'Colección de películas',
            'image_uri' => 'android.resource://com.example.mediaexplorer/drawable/movies'
        ]);

        $series = Category::create([
            'id' => Str::uuid()->toString(),
            'name' => 'Series',
            'description' => 'Colección de series',
            'image_uri' => 'android.resource://com.example.mediaexplorer/drawable/series'
        ]);

        $anime = Category::create([
            'id' => Str::uuid()->toString(),
            'name' => 'Anime',
            'description' => 'Colección de anime',
            'image_uri' => 'android.resource://com.example.mediaexplorer/drawable/anime'
        ]);

        // Crear algunos contenidos de ejemplo
        Content::create([
            'id' => Str::uuid()->toString(),
            'title' => 'El Padrino',
            'tipo' => 'Película',
            'description' => 'Una película clásica de mafiosos',
            'imagen' => 'path/to/image.jpg',
            'categoria_id' => $peliculas->id
        ]);

        Content::create([
            'id' => Str::uuid()->toString(),
            'title' => 'Breaking Bad',
            'tipo' => 'Serie',
            'description' => 'Serie sobre un profesor de química',
            'imagen' => 'path/to/image.jpg',
            'categoria_id' => $series->id
        ]);
    }
}