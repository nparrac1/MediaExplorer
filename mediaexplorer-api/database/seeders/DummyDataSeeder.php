<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\Category;
use App\Models\Content;

class DummyDataSeeder extends Seeder
{
    public function run(): void
    {
        // Crear categorías
        $anime = Category::create([
            'name' => 'Anime',
            'description' => 'Colección de series de anime japonés',
            'imageUri' => 'android.resource://com.example.mediaexplorer/drawable/anime_category'
        ]);

        $movies = Category::create([
            'name' => 'Películas',
            'description' => 'Películas de diferentes géneros',
            'imageUri' => 'android.resource://com.example.mediaexplorer/drawable/movies_category'
        ]);

        $series = Category::create([
            'name' => 'Series',
            'description' => 'Series de televisión',
            'imageUri' => 'android.resource://com.example.mediaexplorer/drawable/series_category'
        ]);

        // Crear contenidos para Anime
        Content::create([
            'title' => 'Attack on Titan',
            'tipo' => 'Anime',
            'description' => 'La humanidad lucha contra los titanes...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/attack_on_titan',
            'categoriaId' => $anime->id
        ]);

        Content::create([
            'title' => 'Death Note',
            'tipo' => 'Anime',
            'description' => 'Un estudiante encuentra un cuaderno sobrenatural...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/death_note',
            'categoriaId' => $anime->id
        ]);

        // Crear contenidos para Películas
        Content::create([
            'title' => 'The Matrix',
            'tipo' => 'Película',
            'description' => 'Un hacker descubre la verdad sobre la realidad...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/matrix',
            'categoriaId' => $movies->id
        ]);

        Content::create([
            'title' => 'Inception',
            'tipo' => 'Película',
            'description' => 'Un ladrón que roba secretos del subconsciente...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/inception',
            'categoriaId' => $movies->id
        ]);

        // Crear contenidos para Series
        Content::create([
            'title' => 'Breaking Bad',
            'tipo' => 'Serie',
            'description' => 'Un profesor de química se convierte en fabricante de drogas...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/breaking_bad',
            'categoriaId' => $series->id
        ]);

        Content::create([
            'title' => 'Stranger Things',
            'tipo' => 'Serie',
            'description' => 'Eventos sobrenaturales en un pequeño pueblo...',
            'imagen' => 'android.resource://com.example.mediaexplorer/drawable/stranger_things',
            'categoriaId' => $series->id
        ]);
    }
}